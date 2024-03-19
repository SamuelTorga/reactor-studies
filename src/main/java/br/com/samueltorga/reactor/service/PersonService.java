package br.com.samueltorga.reactor.service;

import br.com.samueltorga.reactor.controller.dto.PersonCreateDTO;
import br.com.samueltorga.reactor.converter.PersonConverter;
import br.com.samueltorga.reactor.exception.BadRequestException;
import br.com.samueltorga.reactor.model.mongo.Person;
import br.com.samueltorga.reactor.repository.mongo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonService self;

    public Flux<Person> listAll() {
        return personRepository.findAll();
    }

    public Mono<Person> create(PersonCreateDTO person) {
        return create(PersonConverter.INSTANCE.toPersonEntity(person));
    }

    @CachePut(value = "person_id", key = "#person.id")
    public Mono<Person> create(Person person) {
        return personRepository.insert(person);
    }

    @Cacheable(value = "person_id", key = "#id")
    public Mono<Person> findById(String id) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new BadRequestException("Person not found")));
    }

    @CacheEvict(value = "person_id", key = "#id")
    public Mono<Void> deleteById(String id) {
        return self.findById(id).and(personRepository.deleteById(id));
    }

    public Flux<Person> createMany(List<Person> persons) {
        return personRepository.saveAll(persons);
    }

    @CachePut(value = "person_id", key = "#person.id")
    public Mono<Person> updatePerson(Person person) {
        return personRepository.save(person);
    }
}
