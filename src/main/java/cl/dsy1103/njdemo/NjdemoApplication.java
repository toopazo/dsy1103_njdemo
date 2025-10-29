package cl.dsy1103.njdemo;

import java.util.NoSuchElementException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cl.dsy1103.njdemo.model.Person;
import cl.dsy1103.njdemo.repository.PersonRepository;

@SpringBootApplication
public class NjdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NjdemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(PersonRepository repository) {
		return args -> {

			Person person = new Person();
			person.setName("John");

			repository.save(person);
			Person saved = repository.findById(person.getId()).orElseThrow(NoSuchElementException::new);
			System.out.println("Saved person: " + saved);
		};
	}

}
