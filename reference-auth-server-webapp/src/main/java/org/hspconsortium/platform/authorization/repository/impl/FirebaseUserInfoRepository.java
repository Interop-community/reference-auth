package org.hspconsortium.platform.authorization.repository.impl;

import com.google.firebase.auth.UserRecord;
import org.hspconsortium.platform.authentication.persona.PersonaUserInfoRepository;
import org.hspconsortium.platform.service.FirebaseTokenService;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.UserInfo;
import org.springframework.context.annotation.Profile;

import javax.inject.Inject;

@Profile("users-firebase")
public class FirebaseUserInfoRepository extends PersonaUserInfoRepository {
    @Inject
    private FirebaseTokenService firebaseTokenService;

    @Override
    public UserInfo getRealUserByUsername(String username) {
        // validate username against Firebase
        UserRecord userProfileInfo = firebaseTokenService.getUserProfileInfo(username);
        if(userProfileInfo == null)
            return null;

        UserInfo userInfo = new DefaultUserInfo();

        userInfo.setSub(userProfileInfo.getUid());
        userInfo.setPreferredUsername(userProfileInfo.getEmail());
        userInfo.setEmail(userProfileInfo.getEmail());
        userInfo.setName(userProfileInfo.getDisplayName());

        return userInfo;
    }
}
