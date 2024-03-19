package br.com.samueltorga.reactor.converter;

import br.com.samueltorga.reactor.controller.dto.PersonCreateDTO;
import br.com.samueltorga.reactor.model.mongo.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonConverter {

    PersonConverter INSTANCE = Mappers.getMapper(PersonConverter.class);

    Person toPersonEntity(PersonCreateDTO personCreateDTO);

}
