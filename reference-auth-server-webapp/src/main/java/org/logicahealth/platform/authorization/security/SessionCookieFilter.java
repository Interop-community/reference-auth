package org.logicahealth.platform.authorization.security;

import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class SessionCookieFilter implements Filter {
    private final String SESSION_COOKIE_NAME = "JSESSIONID";

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies != null && cookies.length > 0) {
            var cookieList = Arrays.asList(cookies);
            var sessionCookie = cookieList.stream()
                                          .filter(cookie -> SESSION_COOKIE_NAME.equals(cookie.getName()))
                                          .findFirst()
                                          .orElse(null);
            if (sessionCookie != null) {
                String sameSiteAttributeValues = ";Secure;SameSite=None";
                var cookieValue = sessionCookie.getValue();
                if (!cookieValue.contains("Secure") && !cookieValue.contains("SameSite=None")) {
                    resp.setHeader(HttpHeaders.SET_COOKIE, sessionCookie.getName() + "=" + sessionCookie.getValue() + sameSiteAttributeValues);
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
