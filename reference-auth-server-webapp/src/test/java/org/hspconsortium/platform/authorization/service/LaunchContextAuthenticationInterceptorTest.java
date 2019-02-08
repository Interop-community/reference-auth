package org.hspconsortium.platform.authorization.service;

import org.hspconsortium.platform.authentication.launch.LaunchContextAuthenticationInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LaunchContextAuthenticationInterceptorTest {
    public LaunchContextAuthenticationInterceptor launchContextAuthenticationInterceptor;

    @Before
    public void setup() {
        launchContextAuthenticationInterceptor = new LaunchContextAuthenticationInterceptor();
    }

    @Test
    public void testPreHandleIfStringDOesNotStartsWithAuthorize() throws Exception{
        MockHttpServletRequest httpServletRequest = Mockito.mock(MockHttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Mockito.when(httpServletRequest.getServletPath()).thenReturn("/aut");
        boolean result = launchContextAuthenticationInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
        Assert.assertTrue(result);
    }

    @Test
    public void testPreHandleIfHttpMethodGET() throws Exception{
        MockHttpServletRequest httpServletRequest = Mockito.mock(MockHttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Mockito.when(httpServletRequest.getServletPath()).thenReturn("/authorize");
        Mockito.when(httpServletRequest.getMethod()).thenReturn("GET");
        boolean result = launchContextAuthenticationInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
        Assert.assertTrue(result);
    }

    @Test
    public void testPreHandleIfHttpMethodPUT() throws Exception{
        MockHttpServletRequest httpServletRequest = Mockito.mock(MockHttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Mockito.when(httpServletRequest.getServletPath()).thenReturn("/authorize");
        Mockito.when(httpServletRequest.getMethod()).thenReturn("PUT");
        boolean result = launchContextAuthenticationInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
        Assert.assertTrue(result);
    }

    @Test
    public void testPreHandleIfHttpMethodPOST() throws Exception{
        MockHttpServletRequest httpServletRequest = Mockito.mock(MockHttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Mockito.when(httpServletRequest.getServletPath()).thenReturn("/authorize");
        Mockito.when(httpServletRequest.getMethod()).thenReturn("POST");
        Mockito.when(httpServletRequest.getHeader("Referer")).thenReturn("Referer");
        boolean result = launchContextAuthenticationInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
        Assert.assertTrue(result);
    }

    @Test
    public void testPreHandleIfHttpMethodPOSTAndLaunchIdIsNotNull() throws Exception{
        MockHttpServletRequest httpServletRequest = Mockito.mock(MockHttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Mockito.when(httpServletRequest.getServletPath()).thenReturn("/authorize");
        Mockito.when(httpServletRequest.getMethod()).thenReturn("POST");
        Mockito.when(httpServletRequest.getHeader("Referer")).thenReturn("Referer");
        boolean result = launchContextAuthenticationInterceptor.preHandle(httpServletRequest, httpServletResponse, null);
        Assert.assertTrue(result);
    }
}
