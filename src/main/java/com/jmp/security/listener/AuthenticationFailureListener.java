package com.jmp.security.listener;

import com.jmp.security.service.BruteforceHandler;
import com.jmp.security.service.PersonService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final PersonService personService;
    private final BruteforceHandler bruteforceHandler;

    public AuthenticationFailureListener(PersonService personService, BruteforceHandler bruteforceHandler) {
        this.personService = personService;
        this.bruteforceHandler = bruteforceHandler;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof String username && personService.existsByEmailIgnoreCase(username)) {
            bruteforceHandler.onLoginFailed(username);
        }
    }
}
