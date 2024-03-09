package br.com.samueltorga.reactor.service;

import br.com.samueltorga.reactor.controller.dto.ProductCreateDTO;
import br.com.samueltorga.reactor.converter.ProductConverter;
import br.com.samueltorga.reactor.model.Product;
import br.com.samueltorga.reactor.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<Product> listAll() {
        return productRepository.findAll();
    }

    public Mono<Product> findById(Integer id) {
        return productRepository.findById(id)
                .log();
    }

    public Mono<Product> update(Product product) {
        return productRepository.save(product);
    }

    public Mono<Product> createProduct(ProductCreateDTO productCreateDTO) {
        return productRepository.save(ProductConverter.INSTANCE.toProduct(productCreateDTO));
    }

}
