package com.jmp.security.service.security;

import com.jmp.security.entity.Person;
import com.jmp.security.service.BruteforceHandler;
import com.jmp.security.service.PersonService;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonService personService;
    private final BruteforceHandler bruteforceHandler;

    public CustomUserDetailsService(PersonService personService, BruteforceHandler bruteforceHandler) {
        this.personService = personService;
        this.bruteforceHandler = bruteforceHandler;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.getUserByEmail(username);

        if (bruteforceHandler.isBlocked(username)) {
            throw new LockedException("User is blocked");
        }

        String[] authorities = person.getAuthorities().stream()
                .map(authorityEntity -> authorityEntity.getName().name())
                .toArray(String[]::new);

        return withUsername(person.getEmail())
                .password(person.getPassword())
                .authorities(authorities).build();
    }
}
