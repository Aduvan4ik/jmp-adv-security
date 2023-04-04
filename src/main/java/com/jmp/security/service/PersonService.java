package com.jmp.security.service;

import com.jmp.security.entity.Person;

import java.util.List;

public interface PersonService {
    Person getUserByEmail(String email);

    List<Person> findAll();

    boolean existsByEmailIgnoreCase(String email);
}
