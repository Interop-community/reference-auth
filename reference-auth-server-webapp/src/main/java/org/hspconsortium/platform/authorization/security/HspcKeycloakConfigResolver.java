package org.hspconsortium.platform.authorization.security;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class HspcKeycloakConfigResolver implements KeycloakConfigResolver {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.authServerUrl}")
    private String authServerUrl;

    @Value("${keycloak.sslRequired}")
    private String sslRequired;

    @Value("${keycloak.resource}")
    private String resource;

    @Value("${keycloak.verifyTokenAudience}")
    private Boolean verifyTokenAudience;

    @Value("${keycloak.credentials.secret}")
    private String secret;

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {
        AdapterConfig adapterConfig = new AdapterConfig();
        adapterConfig.setRealm(realm);
        adapterConfig.setAuthServerUrl(authServerUrl);
        adapterConfig.setSslRequired(sslRequired);
        adapterConfig.setResource(resource);
        adapterConfig.setVerifyTokenAudience(verifyTokenAudience);
        adapterConfig.setCredentials(Map.of("secret", secret));
        adapterConfig.setConfidentialPort(0);

        return KeycloakDeploymentBuilder.build(adapterConfig);
    }
}
