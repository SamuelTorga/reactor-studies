package br.com.samueltorga.reactor.service;

import br.com.samueltorga.reactor.model.Person;
import br.com.samueltorga.reactor.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class PersonService {

    private final PersonRepository personRepository;

    public Flux<Person> listAll() {
        return personRepository.findAll();
    }
}
