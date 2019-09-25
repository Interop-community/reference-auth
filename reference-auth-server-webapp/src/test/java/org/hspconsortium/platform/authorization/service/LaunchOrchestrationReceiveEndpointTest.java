//package org.hspconsortium.platform.authorization.service;
//
//import org.hspconsortium.platform.authorization.launchcontext.LaunchContextHolder;
//import org.hspconsortium.platform.security.LaunchContext;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.smartplatforms.oauth2.LaunchOrchestrationReceiveEndpoint;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.security.core.context.SecurityContextImpl;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import java.io.PrintWriter;
//
//import static org.mockito.Mockito.*;
//
//public class LaunchOrchestrationReceiveEndpointTest {
//
//    @Mock
//    private LaunchContextHolder launchContextHolder;
//
//    @InjectMocks
//    private LaunchOrchestrationReceiveEndpoint launchOrchestrationReceiveEndpoint;
//
//    private static final String JSON_STRING = "{ parameters :" +
//                                                    "{ launch_id: 123 }" +
//                                               "}";
//
//    @Before
//    public void setup(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testHandleLaunchRequest() throws Exception{
//        HttpServletRequest request = mock(MockHttpServletRequest.class);
//        HttpServletResponse response = mock(MockHttpServletResponse.class);
//        HttpSession sessionObj = mock(MockHttpSession.class);
//        when(request.getSession()).thenReturn(sessionObj);
//        SecurityContextImpl securityContext = mock(SecurityContextImpl.class);
//        LaunchContext launchContext = new LaunchContext();
//        when(sessionObj.getAttribute(any())).thenReturn(null);//securityContext);
//        when(launchContextHolder.getLaunchContext(anyString())).thenReturn(launchContext);
//        PrintWriter printWriter = new PrintWriter("123");
//        when(response.getWriter()).thenReturn(printWriter);
//        launchOrchestrationReceiveEndpoint.handleLaunchRequest(request, response, JSON_STRING);
//
//    }
//}
