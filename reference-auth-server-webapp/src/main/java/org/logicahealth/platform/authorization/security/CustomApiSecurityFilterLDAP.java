package org.logicahealth.platform.authorization.security;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component("customApiSecurityFilter")
@Profile("users-ldap")
public class CustomApiSecurityFilterLDAP implements CustomApiSecurityFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
