package br.com.samueltorga.reactor.service;

import br.com.samueltorga.reactor.controller.dto.ProductCreateDTO;
import br.com.samueltorga.reactor.converter.ProductConverter;
import br.com.samueltorga.reactor.model.r2dbc.Product;
import br.com.samueltorga.reactor.repository.r2dbc.ProductManufacturerRepository;
import br.com.samueltorga.reactor.repository.r2dbc.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductManufacturerRepository productManufacturerRepository;

    public Flux<Product> listAll() {
        return productRepository.findAll(Sort.by(Sort.Order.desc("created")));
    }

    public Mono<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    public Mono<Product> update(Product product) {
        return productRepository.save(product);
    }

    public Mono<Product> createProduct(ProductCreateDTO productCreateDTO) {
        return productRepository.save(ProductConverter.INSTANCE.toProduct(productCreateDTO));
    }

    public Flux<Product> createProduct(List<ProductCreateDTO> products) {
        return productRepository.saveAll(ProductConverter.INSTANCE.toProductList(products));
    }

    public Flux<Product> listAllWithManufacturer() {
        return productManufacturerRepository.listAllProductsWithManufacturers();
    }

    public Flux<Product> listAllWithManufacturer(Integer manufacturerId) {
        return productManufacturerRepository.listAllProductsWithManufacturers(manufacturerId);
    }
}
