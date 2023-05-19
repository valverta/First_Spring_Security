package ru.alishev.First_Spring_Security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.First_Spring_Security.models.Person;
import ru.alishev.First_Spring_Security.services.PersonDetailsService;
import ru.alishev.First_Spring_Security.services.PersonServiceCRUD;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;
    private final PersonServiceCRUD personServiceCRUD;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService, PersonServiceCRUD personServiceCRUD) {
        this.personDetailsService = personDetailsService;
        this.personServiceCRUD = personServiceCRUD;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> name = personServiceCRUD.findByUsername(person.getUsername());
        if (name.isPresent())
            errors.rejectValue("username", "1",
                "Человек с таким именем пользователя уже существует");
    }


    // Версия Алишева, когда не хочется создавать практически одинаковые методы
//    @Override
//    public void validate(Object target, Errors errors) {
//        Person person = (Person) target;
//
//        try {
//            personDetailsService.loadUserByUsername(person.getUsername());
//        } catch (UsernameNotFoundException ignored) {
//            return; // Все ок, пользователь с таким именем не найден
//        }
//
//        errors.rejectValue("username", "1",
//                "Человек с таким именем пользователя уже существует");
//    }
}
