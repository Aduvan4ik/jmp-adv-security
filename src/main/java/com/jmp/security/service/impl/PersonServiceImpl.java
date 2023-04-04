package com.jmp.security.service.impl;

import com.jmp.security.entity.Person;
import com.jmp.security.repository.PersonRepository;
import com.jmp.security.service.PersonService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person getUserByEmail(String email) {
        return personRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("UserEntity not found!"));
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return personRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }
}
