package org.hspconsortium.platform.authorization.security;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.hspconsortium.platform.authorization.repository.impl.LocalUserInfoRepository;
import org.hspconsortium.platform.web.ExtendedUserInfoService;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;

public class HspcKeycloakAuthenticationProvider extends KeycloakAuthenticationProvider {
    @Autowired
    private ExtendedUserInfoService extendedUserInfoService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KeycloakAuthenticationToken auth = (KeycloakAuthenticationToken) super.authenticate(authentication);
        allowOnlyEnterpriseUsers(auth);
        SimpleKeycloakAccount skca = (SimpleKeycloakAccount) auth.getDetails();
        IDToken idToken = skca.getKeycloakSecurityContext().getIdToken();
        DefaultUserInfo userInfo = new DefaultUserInfo();
        userInfo.setSub(idToken.getSubject());
        userInfo.setEmail(idToken.getEmail());
        userInfo.setGivenName(idToken.getGivenName());
        userInfo.setMiddleName(idToken.getMiddleName());
        userInfo.setFamilyName(idToken.getFamilyName());
        userInfo.setName(idToken.getName());
        userInfo.setPreferredUsername(idToken.getEmail());
        // Check if user already exists, if exist then update the user
        DefaultUserInfo existingUser = extendedUserInfoService.getUserInfoBySub(idToken.getSubject());
        if (existingUser != null) {
            userInfo.setId(existingUser.getId());
        }
        extendedUserInfoService.addUserInfo(userInfo);
        return auth;
    }

    private void allowOnlyEnterpriseUsers(KeycloakAuthenticationToken auth) throws AuthenticationException {
        if (!((SimpleKeycloakAccount) auth.getDetails()).getRoles().contains("member")) {
            throw new BadCredentialsException("");
        }
    }
}
