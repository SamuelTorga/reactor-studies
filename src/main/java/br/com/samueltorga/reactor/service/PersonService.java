package br.com.samueltorga.reactor.service;

import br.com.samueltorga.reactor.model.Person;
import br.com.samueltorga.reactor.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<Person> findById(String id) {
        ErrorResponseException error = new ErrorResponseException(HttpStatusCode.valueOf(404));
        error.setDetail("Person not found");
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(error));
    }

    public Mono<Void> deleteById(String id) {
        return findById(id).and(personRepository.deleteById(id));
    }
}
