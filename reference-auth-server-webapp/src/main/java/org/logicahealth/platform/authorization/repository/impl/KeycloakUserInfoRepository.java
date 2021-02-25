package org.logicahealth.platform.authorization.repository.impl;

import org.logicahealth.platform.authentication.persona.PersonaUserInfoRepository;
import org.logicahealth.platform.web.ExtendedUserInfoService;
import org.mitre.openid.connect.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

@Profile("users-keycloak")
public class KeycloakUserInfoRepository extends PersonaUserInfoRepository {

    @Autowired
    private ExtendedUserInfoService extendedUserInfoService;

    @Override
    public UserInfo getRealUserByUsername(String username) {
        UserInfo userInfo = extendedUserInfoService.getUserInfoBySub(username);
        return userInfo;
    }
}

