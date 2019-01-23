package org.mitre.oauth2.web;

import org.mitre.oauth2.model.ClientDetailsEntity;
import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.mitre.oauth2.model.OAuth2RefreshTokenEntity;
import org.mitre.oauth2.service.ClientDetailsEntityService;
import org.mitre.oauth2.service.OAuth2TokenEntityService;
import org.mitre.openid.connect.service.OIDCTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping({"/api/tokens"})
@PreAuthorize("hasRole('ROLE_USER')")
public class TokenAPI {
    public static final String URL = "api/tokens";
    @Autowired
    private OAuth2TokenEntityService tokenService;
    @Autowired
    private HSPCTokenService hspcTokenService;
    @Autowired
    private ClientDetailsEntityService clientService;
    @Autowired
    private OIDCTokenService oidcTokenService;
    private static final Logger logger = LoggerFactory.getLogger(org.mitre.oauth2.web.TokenAPI.class);

    public TokenAPI() {
    }

    @RequestMapping(
            value = {"/access"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public String getAllAccessTokens(ModelMap m, Principal p) {
        Set<OAuth2AccessTokenEntity> allTokens = this.hspcTokenService.getAllAccessTokensForUser(p.getName());
        m.put("entity", allTokens);
        return "tokenApiView";
    }

    @RequestMapping(
            value = {"/access/{id}"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public String getAccessTokenById(@PathVariable("id") Long id, ModelMap m, Principal p) {
        OAuth2AccessTokenEntity token = this.tokenService.getAccessTokenById(id);
        if (token == null) {
            logger.error("getToken failed; token not found: " + id);
            m.put("code", HttpStatus.NOT_FOUND);
            m.put("errorMessage", "The requested token with id " + id + " could not be found.");
            return "jsonErrorView";
        } else if (!token.getAuthenticationHolder().getAuthentication().getName().equals(p.getName())) {
            logger.error("getToken failed; token does not belong to principal " + p.getName());
            m.put("code", HttpStatus.FORBIDDEN);
            m.put("errorMessage", "You do not have permission to view this token");
            return "jsonErrorView";
        } else {
            m.put("entity", token);
            return "tokenApiView";
        }
    }

    @RequestMapping(
            value = {"/access/{id}"},
            method = {RequestMethod.DELETE},
            produces = {"application/json"}
    )
    public String deleteAccessTokenById(@PathVariable("id") Long id, ModelMap m, Principal p) {
        OAuth2AccessTokenEntity token = this.tokenService.getAccessTokenById(id);
        if (token == null) {
            logger.error("getToken failed; token not found: " + id);
            m.put("code", HttpStatus.NOT_FOUND);
            m.put("errorMessage", "The requested token with id " + id + " could not be found.");
            return "jsonErrorView";
        } else if (!token.getAuthenticationHolder().getAuthentication().getName().equals(p.getName())) {
            logger.error("getToken failed; token does not belong to principal " + p.getName());
            m.put("code", HttpStatus.FORBIDDEN);
            m.put("errorMessage", "You do not have permission to view this token");
            return "jsonErrorView";
        } else {
            this.tokenService.revokeAccessToken(token);
            return "httpCodeView";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(
            value = {"/client/{clientId}"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public String getAccessTokensByClientId(@PathVariable("clientId") String clientId, ModelMap m, Principal p) {
        ClientDetailsEntity client = this.clientService.loadClientByClientId(clientId);
        if (client != null) {
            List<OAuth2AccessTokenEntity> tokens = this.tokenService.getAccessTokensForClient(client);
            m.put("entity", tokens);
            return "tokenApiView";
        } else {
            m.put("code", HttpStatus.NOT_FOUND);
            m.put("errorMessage", "The requested client with id " + clientId + " could not be found.");
            return "jsonErrorView";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(
            value = {"/registration/{clientId}"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public String getRegistrationTokenByClientId(@PathVariable("clientId") String clientId, ModelMap m, Principal p) {
        ClientDetailsEntity client = this.clientService.loadClientByClientId(clientId);
        if (client != null) {
            OAuth2AccessTokenEntity token = this.tokenService.getRegistrationAccessTokenForClient(client);
            if (token != null) {
                m.put("entity", token);
                return "tokenApiView";
            } else {
                m.put("code", HttpStatus.NOT_FOUND);
                m.put("errorMessage", "No registration token could be found.");
                return "jsonErrorView";
            }
        } else {
            m.put("code", HttpStatus.NOT_FOUND);
            m.put("errorMessage", "The requested client with id " + clientId + " could not be found.");
            return "jsonErrorView";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(
            value = {"/registration/{clientId}"},
            method = {RequestMethod.PUT},
            produces = {"application/json"}
    )
    public String rotateRegistrationTokenByClientId(@PathVariable("clientId") String clientId, ModelMap m, Principal p) {
        ClientDetailsEntity client = this.clientService.loadClientByClientId(clientId);
        if (client != null) {
            OAuth2AccessTokenEntity token = this.oidcTokenService.rotateRegistrationAccessTokenForClient(client);
            token = this.tokenService.saveAccessToken(token);
            if (token != null) {
                m.put("entity", token);
                return "tokenApiView";
            } else {
                m.put("code", HttpStatus.NOT_FOUND);
                m.put("errorMessage", "No registration token could be found.");
                return "jsonErrorView";
            }
        } else {
            m.put("code", HttpStatus.NOT_FOUND);
            m.put("errorMessage", "The requested client with id " + clientId + " could not be found.");
            return "jsonErrorView";
        }
    }

    @RequestMapping(
            value = {"/refresh"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public String getAllRefreshTokens(ModelMap m, Principal p) {
        Set<OAuth2RefreshTokenEntity> allTokens = this.tokenService.getAllRefreshTokensForUser(p.getName());
        m.put("entity", allTokens);
        return "tokenApiView";
    }

    @RequestMapping(
            value = {"/refresh/{id}"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public String getRefreshTokenById(@PathVariable("id") Long id, ModelMap m, Principal p) {
        OAuth2RefreshTokenEntity token = this.tokenService.getRefreshTokenById(id);
        if (token == null) {
            logger.error("refresh token not found: " + id);
            m.put("code", HttpStatus.NOT_FOUND);
            m.put("errorMessage", "The requested token with id " + id + " could not be found.");
            return "jsonErrorView";
        } else if (!token.getAuthenticationHolder().getAuthentication().getName().equals(p.getName())) {
            logger.error("refresh token " + id + " does not belong to principal " + p.getName());
            m.put("code", HttpStatus.FORBIDDEN);
            m.put("errorMessage", "You do not have permission to view this token");
            return "jsonErrorView";
        } else {
            m.put("entity", token);
            return "tokenApiView";
        }
    }

    @RequestMapping(
            value = {"/refresh/{id}"},
            method = {RequestMethod.DELETE},
            produces = {"application/json"}
    )
    public String deleteRefreshTokenById(@PathVariable("id") Long id, ModelMap m, Principal p) {
        OAuth2RefreshTokenEntity token = this.tokenService.getRefreshTokenById(id);
        if (token == null) {
            logger.error("refresh token not found: " + id);
            m.put("code", HttpStatus.NOT_FOUND);
            m.put("errorMessage", "The requested token with id " + id + " could not be found.");
            return "jsonErrorView";
        } else if (!token.getAuthenticationHolder().getAuthentication().getName().equals(p.getName())) {
            logger.error("refresh token " + id + " does not belong to principal " + p.getName());
            m.put("code", HttpStatus.FORBIDDEN);
            m.put("errorMessage", "You do not have permission to view this token");
            return "jsonErrorView";
        } else {
            this.tokenService.revokeRefreshToken(token);
            return "httpCodeView";
        }
    }
}
