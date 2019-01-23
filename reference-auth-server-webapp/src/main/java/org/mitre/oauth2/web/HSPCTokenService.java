package org.mitre.oauth2.web;

import com.google.common.collect.Sets;
import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.mitre.openid.connect.web.HSPCOauth2TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

@Service
public class HSPCTokenService {

    private static final Logger logger = LoggerFactory.getLogger(HSPCTokenService.class);

    private HSPCOauth2TokenRepository tokenRepository;

    public HSPCTokenService(HSPCOauth2TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Set<OAuth2AccessTokenEntity> getAllAccessTokensForUser(String id) {
        return this.tokenRepository.getAllAccessTokensForUser("jsmith@kolbrich1");
    }

}
