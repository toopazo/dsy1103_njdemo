package cl.dsy1103.njdemo.repository;

// import org.springframework.data.repository.Repository;
// import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import cl.dsy1103.njdemo.model.Person;

// public interface PersonRepository extends Repository<Person, Long> {
public interface PersonRepository extends JpaRepository<Person, Long> {

}