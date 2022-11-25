package com.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, 
    		AuthenticationException exception) throws IOException, ServletException {

        String msg = "";

        // exception 관련 메세지 처리
        if (exception instanceof UsernameNotFoundException) {
            msg = "ID_NOT_FOUND";
        } else if (exception instanceof BadCredentialsException) {
            msg = "AUTHORITY_NOT_PERMITTED";
        }

        setDefaultFailureUrl("/member/login?message=" + msg);
        super.onAuthenticationFailure(request, response, exception);

    }
}