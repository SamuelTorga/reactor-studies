package br.com.samueltorga.reactor.controller;

import br.com.samueltorga.reactor.controller.dto.ProductCreateDTO;
import br.com.samueltorga.reactor.exception.BadRequestException;
import br.com.samueltorga.reactor.exception.InfraException;
import br.com.samueltorga.reactor.model.r2dbc.Product;
import br.com.samueltorga.reactor.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequestMapping("products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public Flux<Product> listAll() {
        return productService.listAll();
    }

    @GetMapping("manufacturers")
    public Flux<Product> listAllByManufacturer() {
        return productService.listAllWithManufacturer();
    }

    @GetMapping("manufacturers/{manufacturerId}")
    public Flux<Product> listAllByManufacturer(@PathVariable @NotNull Integer manufacturerId) {
        return productService.listAllWithManufacturer(manufacturerId);
    }

    @GetMapping(value = "stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> listAllStream() {
        return productService.listAll();
    }

    @GetMapping("{id}")
    public Mono<Product> findById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @PatchMapping(value = "{id}", consumes = {"application/json-patch+json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> partialUpdate(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        return findById(id)
                .flatMap(product -> {
                    try {
                        return Mono.just(applyPatchTo(patch, product));
                    } catch (JsonPatchException e) {
                        return Mono.error(new BadRequestException(e));
                    } catch (Exception e) {
                        return Mono.error(new InfraException(e));
                    }
                })
                .flatMap(productService::update);
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        return productService.createProduct(productCreateDTO);
    }

    @PostMapping("/batch")
    public Flux<Product> createManyProduct(@RequestBody List<ProductCreateDTO> products) {
        return productService.createProduct(products);
    }

    private Product applyPatchTo(JsonPatch patch, Product product) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(product, JsonNode.class));
        return objectMapper.treeToValue(patched, Product.class);
    }

}
