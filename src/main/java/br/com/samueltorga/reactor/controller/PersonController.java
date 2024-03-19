package br.com.samueltorga.reactor.controller;

import br.com.samueltorga.reactor.controller.dto.PersonCreateDTO;
import br.com.samueltorga.reactor.exception.BadRequestException;
import br.com.samueltorga.reactor.exception.InfraException;
import br.com.samueltorga.reactor.model.mongo.Person;
import br.com.samueltorga.reactor.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
    private final ObjectMapper objectMapper;

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
    public Mono<Person> createPerson(@Valid @RequestBody PersonCreateDTO person) {
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

    @PatchMapping(value = "{id}", consumes = {"application/json-patch+json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> partialUpdate(@PathVariable String id, @RequestBody JsonPatch patch) {
        return findById(id)
                .flatMap(person -> {
                    try {
                        return Mono.just(applyPatchTo(patch, person));
                    } catch (JsonPatchException e) {
                        return Mono.error(new BadRequestException(e));
                    } catch (Exception e) {
                        return Mono.error(new InfraException(e));
                    }
                })
                .flatMap(personService::updatePerson);
    }

    private Person applyPatchTo(JsonPatch patch, Person person) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(person, JsonNode.class));
        return objectMapper.treeToValue(patched, Person.class);
    }

}
