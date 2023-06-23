package br.com.samueltorga.reactor.controller;

import br.com.samueltorga.reactor.model.Person;
import br.com.samueltorga.reactor.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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

}
