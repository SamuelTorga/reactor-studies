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

    @PostMapping
    public Mono<Person> createPerson(@Valid @RequestBody Person person) {
        return personService.create(person);
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
