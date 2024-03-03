package br.com.samueltorga.reactor.service;

import br.com.samueltorga.reactor.model.Person;
import br.com.samueltorga.reactor.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PersonService {

    private final PersonRepository personRepository;

    public Flux<Person> listAll() {
        return personRepository.findAll();
    }

    public Mono<Person> create(Person person) {
        return personRepository.save(person);
    }

    @Cacheable(value = "person_id", key = "#id")
    public Mono<Person> findById(String id) {
        ErrorResponseException error = new ErrorResponseException(HttpStatusCode.valueOf(404));
        error.setDetail("Person not found");
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(error));
    }

    @CacheEvict(value = "person_id", key = "#id")
    public Mono<Void> deleteById(String id) {
        return findById(id).and(personRepository.deleteById(id));
    }

    public Flux<Person> createMany(List<Person> persons) {
        return personRepository.saveAll(persons);
    }
}
