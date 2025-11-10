package cl.dsy1103.njdemo.controller;

import java.util.stream.Collectors;
import java.util.Optional;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;

import cl.dsy1103.njdemo.model.Person;
import cl.dsy1103.njdemo.repository.PersonRepository;
import cl.dsy1103.njdemo.assembler.PersonModelAssembler;

@RestController
@CrossOrigin(origins = "http://localhost:3000/") // Allows requests from localhost:3000
public class PersonController {
    private final PersonRepository repository;
    private final PersonModelAssembler assembler;

    PersonController(PersonRepository repository, PersonModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;

    }

    @GetMapping("/api/v1/recursos")
    public ResponseEntity<CollectionModel<EntityModel<Person>>> all() {
        List<EntityModel<Person>> persons = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(CollectionModel.of(persons, linkTo(methodOn(PersonController.class).all()).withSelfRel()));
    }

    @PostMapping("/api/v1/recursos")
    public ResponseEntity<?> newPerson(@RequestBody Person newPerson) {
        EntityModel<Person> entityModel = assembler.toModel(repository.save(newPerson));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // Single item

    @GetMapping("/api/v1/recursos/{id}")
    public ResponseEntity<EntityModel<Person>> getById(@PathVariable Long id) {
        Person employee = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        return ResponseEntity.ok(assembler.toModel(employee));
    }

    @PutMapping("/api/v1/recursos/{id}")
    public ResponseEntity<EntityModel<Person>> replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {

        Optional<Person> optionalPerson = repository.findById(id);
        System.out.println("Optional person: " + optionalPerson);
        Person person;
        if (optionalPerson.isEmpty()) {
            // newPerson.setId(id);
            person = repository.save(newPerson);

            EntityModel<Person> entityModel = assembler.toModel(person);
            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        } else {
            person = optionalPerson.get();
            person.setName(newPerson.getName());
            person.setRole(newPerson.getRole());
            person.setAge(newPerson.getAge());
            repository.save(person);

            EntityModel<Person> entityModel = assembler.toModel(person);
            return ResponseEntity.ok(entityModel);
        }
    }

    @DeleteMapping("/api/v1/recursos/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
