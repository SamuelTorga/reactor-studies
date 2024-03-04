package br.com.samueltorga.reactor.converter;

import br.com.samueltorga.reactor.controller.dto.PersonCreateDTO;
import br.com.samueltorga.reactor.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonConveter {

    PersonConveter INSTANCE = Mappers.getMapper(PersonConveter.class);

    Person toPersonEntity(PersonCreateDTO personCreateDTO);

}
