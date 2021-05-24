package org.logicahealth.platform.authorization.security;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SessionCookieFilter extends GenericFilterBean implements CustomApiSecurityFilter {

    private final String SESSION_COOKIE_NAME = "JSESSIONID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies != null && cookies.length > 0) {
            List<Cookie> cookieList = Arrays.asList(cookies);
            Cookie sessionCookie = cookieList.stream()
                                             .filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName()))
                                             .findFirst()
                                             .orElse(null);
            if (sessionCookie != null) {
                String sameSiteAttributeValues = ";HttpOnly;Secure;SameSite=None";
                resp.setHeader(HttpHeaders.SET_COOKIE, sessionCookie.getName() + "=" + sessionCookie.getValue() + sameSiteAttributeValues);
            }
        }
        chain.doFilter(request, response);
    }
}
