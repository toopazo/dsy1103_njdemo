package cl.dsy1103.njdemo;

import java.util.NoSuchElementException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// import com.github.javafaker.Faker;
// import java.util.Locale;

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

			// Faker faker = new Faker(Locale.forLanguageTag("es-CL"));

			Person person = new Person();
			// person.setName(faker.name().fullName());
			// person.setAge(faker.number().numberBetween(18, 65));
			// person.setRole(faker.job().title());
			person.setName("Juan Perez");
			person.setAge(30);
			person.setRole("Poet");

			repository.save(person);
			Person saved = repository.findById(person.getId()).orElseThrow(NoSuchElementException::new);
			System.out.println("Saved person: " + saved);
		};
	}

}
