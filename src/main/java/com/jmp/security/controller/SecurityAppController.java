package com.jmp.security.controller;

import com.jmp.security.entity.Person;
import com.jmp.security.service.BruteforceHandler;
import com.jmp.security.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class SecurityAppController {

    private final PersonService personService;
    private final BruteforceHandler bruteforceHandler;

    public SecurityAppController(PersonService personService, BruteforceHandler bruteforceHandler) {
        this.personService = personService;
        this.bruteforceHandler = bruteforceHandler;
    }

    @GetMapping("/info")
    public String getInfo() {
        return "info";
    }

    @GetMapping("/about")
    public String getAboutInfo() {
        return "about";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin";
    }

    @GetMapping("/login")
    public String getLoginPage(ModelMap model, @RequestParam Optional<String> error) {
        error.ifPresent(err -> model.addAttribute("error", err));
        return "login";
    }

    @GetMapping("/logoutSuccess")
    public String logoutSuccessPage() {
        return "logout";
    }

    @GetMapping("/blocked")
    public String getBlockedUsers(Model model) {
        List<Person> persons = personService.findAll();
        Map<String, Object> blockedUsers = persons.stream()
                .map(Person::getEmail)
                .filter(bruteforceHandler::isBlocked)
                .collect(Collectors.toMap(user -> user,
                        user -> bruteforceHandler.getCachedValue(user).getBlockedTimestamp()));

        model.addAttribute("blockedUsers", blockedUsers);

        return "blocked_users";
    }

}
