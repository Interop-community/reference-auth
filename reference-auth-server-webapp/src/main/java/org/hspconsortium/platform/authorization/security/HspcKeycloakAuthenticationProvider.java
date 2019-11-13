package org.hspconsortium.platform.authorization.security;

import org.hspconsortium.platform.authorization.repository.impl.LocalUserInfoRepository;
import org.hspconsortium.platform.web.ExtendedUserInfoService;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class HspcKeycloakAuthenticationProvider extends KeycloakAuthenticationProvider {
    @Autowired
    private ExtendedUserInfoService extendedUserInfoService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KeycloakAuthenticationToken auth = (KeycloakAuthenticationToken) super.authenticate(authentication);
        SimpleKeycloakAccount skca = (SimpleKeycloakAccount) auth.getDetails();
        IDToken idToken = skca.getKeycloakSecurityContext().getIdToken();
        DefaultUserInfo userInfo = new DefaultUserInfo();
        userInfo.setSub(idToken.getSubject());
        // TODO:  set all the other fields needed
        // Check if user already exists, if exist then update the user
        // look for a method
        extendedUserInfoService.addUserInfo(userInfo);
        // Add a method to get user_info by sub
//       extendedUserInfoService.getUserInfoBySub(sub)

        return auth;
    }
}
