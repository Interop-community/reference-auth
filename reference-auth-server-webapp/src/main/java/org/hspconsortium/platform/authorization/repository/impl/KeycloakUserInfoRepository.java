package org.hspconsortium.platform.authorization.repository.impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hspconsortium.platform.authentication.persona.PersonaUserInfoRepository;
import org.hspconsortium.platform.web.ExtendedUserInfoService;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.apache.http.*;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;

@Profile("users-keycloak")
public class KeycloakUserInfoRepository extends PersonaUserInfoRepository {
    @Value("${hspc.platform.keycloak.userInfoEndpointUrl}")
    String userInfoEndpointUrl;

    @Autowired
    private ExtendedUserInfoService extendedUserInfoService;

    @Override
    public UserInfo getRealUserByUsername(String username) {
        UserInfo userInfo = extendedUserInfoService.getUserInfoBySub(username);
        return userInfo;
    }
}

