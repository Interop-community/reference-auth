//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.mitre.oauth2.service.impl;

import org.mitre.data.AbstractPageOperationTemplate;
import org.mitre.oauth2.model.AuthenticationHolderEntity;
import org.mitre.oauth2.model.AuthorizationCodeEntity;
import org.mitre.oauth2.repository.AuthenticationHolderRepository;
import org.mitre.oauth2.repository.AuthorizationCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@Service("defaultOAuth2AuthorizationCodeService")
public class DefaultOAuth2AuthorizationCodeService implements AuthorizationCodeServices {
    private static final Logger logger = LoggerFactory.getLogger(DefaultOAuth2AuthorizationCodeService.class);
    @Autowired
    private AuthorizationCodeRepository repository;
    @Autowired
    private AuthenticationHolderRepository authenticationHolderRepository;
    private int authCodeExpirationSeconds = 300;
    private RandomValueStringGenerator generator = new RandomValueStringGenerator();

    public DefaultOAuth2AuthorizationCodeService() {
    }

    @Transactional("defaultTransactionManager")
    @Retryable(value = RuntimeException.class)
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = this.generator.generate();
        AuthenticationHolderEntity authHolder = new AuthenticationHolderEntity();
        authHolder.setAuthentication(authentication);
        authHolder = this.authenticationHolderRepository.save(authHolder);
        Date expiration = new Date(System.currentTimeMillis() + (long)this.getAuthCodeExpirationSeconds() * 1000L);
        AuthorizationCodeEntity entity = new AuthorizationCodeEntity(code, authHolder, expiration);
        this.repository.save(entity);
        return code;
    }

    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
        AuthorizationCodeEntity result = this.repository.getByCode(code);
        if (result == null) {
            throw new InvalidGrantException("JpaAuthorizationCodeRepository: no authorization code found for value " + code);
        } else {
            OAuth2Authentication auth = result.getAuthenticationHolder().getAuthentication();
            this.repository.remove(result);
            return auth;
        }
    }

    @Transactional("defaultTransactionManager")
    public void clearExpiredAuthorizationCodes() {
        (new AbstractPageOperationTemplate<AuthorizationCodeEntity>("clearExpiredAuthorizationCodes") {
            public Collection<AuthorizationCodeEntity> fetchPage() {
                return DefaultOAuth2AuthorizationCodeService.this.repository.getExpiredCodes();
            }

            protected void doOperation(AuthorizationCodeEntity item) {
                DefaultOAuth2AuthorizationCodeService.this.repository.remove(item);
            }
        }).execute();
    }

    public AuthorizationCodeRepository getRepository() {
        return this.repository;
    }

    public void setRepository(AuthorizationCodeRepository repository) {
        this.repository = repository;
    }

    public int getAuthCodeExpirationSeconds() {
        return this.authCodeExpirationSeconds;
    }

    public void setAuthCodeExpirationSeconds(int authCodeExpirationSeconds) {
        this.authCodeExpirationSeconds = authCodeExpirationSeconds;
    }
}
