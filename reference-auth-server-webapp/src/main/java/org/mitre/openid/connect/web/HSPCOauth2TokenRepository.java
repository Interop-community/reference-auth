package org.mitre.openid.connect.web;

import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.mitre.oauth2.model.OAuth2RefreshTokenEntity;
import org.mitre.oauth2.repository.impl.JpaOAuth2TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.LinkedHashSet;
import java.util.Set;

@Repository
public class HSPCOauth2TokenRepository {

    private static final Logger logger = LoggerFactory.getLogger(HSPCOauth2TokenRepository.class);
    @PersistenceContext(
            unitName = "defaultPersistenceUnit"
    )
    private EntityManager manager;

    public Set<OAuth2AccessTokenEntity> getAllAccessTokensForUser(String id) {
        TypedQuery<OAuth2AccessTokenEntity> query = this.manager.createNamedQuery("OAuth2AccessTokenEntity.getByUser", OAuth2AccessTokenEntity.class);
        query.setParameter("user", id);
        return new LinkedHashSet(query.getResultList());
    }

    public Set<OAuth2RefreshTokenEntity> getAllRefreshTokensForUser(String id) {
        TypedQuery<OAuth2RefreshTokenEntity> query = this.manager.createNamedQuery("OAuth2RefreshTokenEntity.getByUser", OAuth2RefreshTokenEntity.class);
        query.setParameter("user", id);
        return new LinkedHashSet(query.getResultList());
    }
}
