package org.hspconsortium.platform.authorization.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component("customApiSecurityFilter")
@Profile("users-keycloak")
public class CustomApiSecurityFilterKeycloak extends GenericFilterBean implements CustomApiSecurityFilter {

    private final Logger log = LoggerFactory.getLogger(CustomApiSecurityFilterKeycloak.class);
    public CustomApiSecurityFilterKeycloak() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        //TODO: use Spring security SimpleAuthorityMapper to insert ROLE_ and org.mitre.oauth2.model.convert role name to uppercase
        SimpleAuthorityMapper simpleAuthorityMapper = new SimpleAuthorityMapper();
        simpleAuthorityMapper.setDefaultAuthority(servletRequest.toString());

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
