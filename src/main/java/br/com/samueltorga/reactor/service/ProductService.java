package br.com.samueltorga.reactor.service;

import br.com.samueltorga.reactor.model.Product;
import br.com.samueltorga.reactor.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<Product> listAll() {
        return productRepository.findAll();
    }

    public Mono<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    public Mono<Product> update(Product product) {
        return productRepository.save(product);
    }
}
