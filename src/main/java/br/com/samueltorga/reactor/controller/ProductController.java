package br.com.samueltorga.reactor.controller;

import br.com.samueltorga.reactor.exception.BadRequestException;
import br.com.samueltorga.reactor.exception.InfraException;
import br.com.samueltorga.reactor.model.Product;
import br.com.samueltorga.reactor.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    private Product applyPatchTo(JsonPatch patch, Product product) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(product, JsonNode.class));
        return objectMapper.treeToValue(patched, Product.class);
    }

}
