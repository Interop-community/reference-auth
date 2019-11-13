package org.hspconsortium.platform.authorization.repository.impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hspconsortium.platform.authentication.persona.PersonaUserInfoRepository;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.UserInfo;
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

    @Override
    public UserInfo getRealUserByUsername(String username) {
        SecurityContext sc = SecurityContextHolder.getContext();

        UserInfo userInfo = new DefaultUserInfo();
        userInfo.setSub("4fa1fb37-13ab-4019-bef2-ec3ce53aa91e");
        userInfo.setPreferredUsername("name");
        userInfo.setEmail("email@email.com");
        userInfo.setName("displayName");
//
//        // TODO: populate userInfo
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        try {
//            HttpGet request = new HttpGet(userInfoEndpointUrl);
//            // TODO: set headers (token)
//            CloseableHttpResponse response = httpClient.execute(request);
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
//                JSONParser parser = new JSONParser();
//                JSONObject oicUserInfo = (JSONObject) parser.parse(responseString);
//
////                userInfo.setSub(oicUserInfo.get("sub").toString());
////                userInfo.setPreferredUsername(oicUserInfo.get("username").toString());
////                userInfo.setEmail(oicUserInfo.get("email").toString());
////                userInfo.setName(oicUserInfo.get("name").toString());
//            }
//        } catch (Exception e) {
//
//        }

        return userInfo;
    }
}

