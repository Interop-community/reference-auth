package org.mitre.oauth2.web;

import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.mitre.oauth2.model.OAuth2RefreshTokenEntity;
import org.mitre.openid.connect.web.HSPCOauth2TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HSPCTokenService {

    private static final Logger logger = LoggerFactory.getLogger(HSPCTokenService.class);

    private HSPCOauth2TokenRepository accessTokenRepository;

    public HSPCTokenService(HSPCOauth2TokenRepository tokenRepository) {
        this.accessTokenRepository = tokenRepository;
    }

    public Set<OAuth2AccessTokenEntity> getAllAccessTokensForUser(String id) {
        return this.accessTokenRepository.getAllAccessTokensForUser(id);
    }

    public Set<OAuth2RefreshTokenEntity> getAllRefreshTokensForUser(String id) {
        return this.accessTokenRepository.getAllRefreshTokensForUser(id);
    }

}
