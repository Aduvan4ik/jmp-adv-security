package com.jmp.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String USER_IS_BLOCKED = "User is blocked";
    private static final String BAD_CREDENTIALS = "Bad Credentials";


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
        String errorMessage = BAD_CREDENTIALS;

        if (exception.getMessage().equalsIgnoreCase(USER_IS_BLOCKED)) {
            errorMessage = USER_IS_BLOCKED;
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }

}
