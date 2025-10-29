package cl.dsy1103.njdemo.repository;

import org.springframework.data.repository.Repository;

import cl.dsy1103.njdemo.model.Person;

import java.util.Optional;

public interface PersonRepository extends Repository<Person, Long> {

    Person save(Person person);

    Optional<Person> findById(long id);
}