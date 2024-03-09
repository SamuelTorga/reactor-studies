package br.com.samueltorga.reactor.converter;

import br.com.samueltorga.reactor.controller.dto.ProductCreateDTO;
import br.com.samueltorga.reactor.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductConverter {

    ProductConverter INSTANCE = Mappers.getMapper(ProductConverter.class);

    Product toProduct(ProductCreateDTO productCreateDTO);

}
