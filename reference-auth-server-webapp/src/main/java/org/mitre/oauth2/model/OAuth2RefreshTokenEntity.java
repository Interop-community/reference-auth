//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.mitre.oauth2.model;

import com.nimbusds.jwt.JWT;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.mitre.oauth2.model.convert.JWTStringConverter;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

@Entity
@Table(
        name = "refresh_token"
)
@NamedQueries({@NamedQuery(
        name = "OAuth2RefreshTokenEntity.getAll",
        query = "select r from OAuth2RefreshTokenEntity r"
), @NamedQuery(
        name = "OAuth2RefreshTokenEntity.getAllExpiredByDate",
        query = "select r from OAuth2RefreshTokenEntity r where r.expiration <= :date"
), @NamedQuery(
        name = "OAuth2RefreshTokenEntity.getByClient",
        query = "select r from OAuth2RefreshTokenEntity r where r.client = :client"
), @NamedQuery(
        name = "OAuth2RefreshTokenEntity.getByTokenValue",
        query = "select r from OAuth2RefreshTokenEntity r where r.jwt = :tokenValue"
),@NamedQuery(
        name = OAuth2RefreshTokenEntity.QUERY_BY_USER,
        query = "select a from OAuth2RefreshTokenEntity a WHERE a.authenticationHolder.id IN (SELECT b.id FROM AuthenticationHolderEntity b WHERE b.userAuth.id IN (SELECT c.id FROM SavedUserAuthentication c WHERE c.name = :" + OAuth2AccessTokenEntity.PARAM_USER + ")) AND (a.expiration > CURRENT_TIMESTAMP OR a.expiration IS NULL)")
})
public class OAuth2RefreshTokenEntity implements OAuth2RefreshToken {
    public static final String QUERY_BY_TOKEN_VALUE = "OAuth2RefreshTokenEntity.getByTokenValue";
    public static final String QUERY_BY_CLIENT = "OAuth2RefreshTokenEntity.getByClient";
    public static final String QUERY_EXPIRED_BY_DATE = "OAuth2RefreshTokenEntity.getAllExpiredByDate";
    public static final String QUERY_ALL = "OAuth2RefreshTokenEntity.getAll";
    public static final String PARAM_TOKEN_VALUE = "tokenValue";
    public static final String PARAM_CLIENT = "client";
    public static final String PARAM_DATE = "date";
    public static final String QUERY_BY_USER = "OAuth2RefreshTokenEntity.getByUser";

    public static final String PARAM_USER = "user";

    private Long id;
    private AuthenticationHolderEntity authenticationHolder;
    private ClientDetailsEntity client;
    private JWT jwt;
    private Date expiration;

    public OAuth2RefreshTokenEntity() {
    }

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(
            name = "auth_holder_id"
    )
    public AuthenticationHolderEntity getAuthenticationHolder() {
        return this.authenticationHolder;
    }

    public void setAuthenticationHolder(AuthenticationHolderEntity authenticationHolder) {
        this.authenticationHolder = authenticationHolder;
    }

    @Transient
    public String getValue() {
        return this.jwt.serialize();
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "expiration"
    )
    public Date getExpiration() {
        return this.expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    @Transient
    public boolean isExpired() {
        return this.getExpiration() == null ? false : System.currentTimeMillis() > this.getExpiration().getTime();
    }

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "client_id"
    )
    public ClientDetailsEntity getClient() {
        return this.client;
    }

    public void setClient(ClientDetailsEntity client) {
        this.client = client;
    }

    @Basic
    @Column(
            name = "token_value"
    )
    @Convert(
            converter = JWTStringConverter.class
    )
    public JWT getJwt() {
        return this.jwt;
    }

    public void setJwt(JWT jwt) {
        this.jwt = jwt;
    }
}
