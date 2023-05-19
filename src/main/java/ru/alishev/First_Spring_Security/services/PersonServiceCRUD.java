package ru.alishev.First_Spring_Security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.First_Spring_Security.models.Person;
import ru.alishev.First_Spring_Security.repositories.PeopleRepository;

import java.util.Optional;

@Service
public class PersonServiceCRUD {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public PersonServiceCRUD(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder1;
    }

    public Optional<Person> findByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}
