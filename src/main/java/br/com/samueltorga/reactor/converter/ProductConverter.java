package br.com.samueltorga.reactor.converter;

import br.com.samueltorga.reactor.controller.dto.ProductCreateDTO;
import br.com.samueltorga.reactor.model.r2dbc.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductConverter {

    ProductConverter INSTANCE = Mappers.getMapper(ProductConverter.class);

    Product toProduct(ProductCreateDTO productCreateDTO);

    List<Product> toProductList(List<ProductCreateDTO> products);

}
