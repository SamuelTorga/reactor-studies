package br.com.samueltorga.reactor.controller;

import br.com.samueltorga.reactor.model.Person;
import br.com.samueltorga.reactor.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequestMapping("persons")
@Slf4j
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public Flux<Person> listAll() {
        return personService.listAll();
    }

    @GetMapping(value = "stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> listAllStream() {
        return personService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> createPerson(@Valid @RequestBody Person person) {
        return personService.create(person);
    }

    @PostMapping("batch")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Person> createManyPersons(@Valid @RequestBody List<Person> persons) {
        return personService.createMany(persons);
    }

    @GetMapping("{id}")
    public Mono<Person> findById(@PathVariable String id) {
        return personService.findById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable String id) {
        return personService.deleteById(id);
    }

}
