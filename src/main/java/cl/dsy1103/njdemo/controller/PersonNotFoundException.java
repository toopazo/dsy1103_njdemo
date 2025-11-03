package cl.dsy1103.njdemo.controller;

public class PersonNotFoundException extends RuntimeException {

    PersonNotFoundException(Long id) {
        super("Could not find person " + id);
    }
}
