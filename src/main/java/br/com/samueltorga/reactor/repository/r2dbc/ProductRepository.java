package br.com.samueltorga.reactor.repository.r2dbc;

import br.com.samueltorga.reactor.model.r2dbc.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends R2dbcRepository<Product, Integer> {
}
