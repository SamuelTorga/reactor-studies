package br.com.samueltorga.reactor.repository;

import br.com.samueltorga.reactor.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
}
