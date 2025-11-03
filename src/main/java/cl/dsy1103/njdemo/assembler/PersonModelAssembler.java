package cl.dsy1103.njdemo.assembler;

import cl.dsy1103.njdemo.model.Person;
import cl.dsy1103.njdemo.controller.PersonController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PersonModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {

    @Override
    public EntityModel<Person> toModel(Person employee) {

        return EntityModel.of(employee, //
                linkTo(methodOn(PersonController.class).getById(employee.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).all()).withRel("employees"));
    }
}