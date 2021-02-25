package org.logicahealth.platform.authorization.security;

import org.logicahealth.platform.authentication.persona.PersonaAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof PersonaAuthenticationToken) {
            response.setHeader("Location",  this.getDefaultTargetUrl());
            response.setStatus(307);
            return;
        }
        super.onLogoutSuccess(request, response, authentication);
    }
}
