package ru.alishev.First_Spring_Security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alishev.First_Spring_Security.models.Person;
import ru.alishev.First_Spring_Security.services.PersonServiceCRUD;
import ru.alishev.First_Spring_Security.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final PersonServiceCRUD personServiceCRUD;

    @Autowired
    public AuthController(PersonValidator personValidator, PersonServiceCRUD personServiceCRUD) {
        this.personValidator = personValidator;
        this.personServiceCRUD = personServiceCRUD;
    }

    @GetMapping("/login")
    public String signIn() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("person", new Person());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        BindingResult bi = bindingResult;
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors() || bi.hasErrors())
            return "auth/registration";

        personServiceCRUD.register(person);

        return "redirect:/auth/login";
    }
}
