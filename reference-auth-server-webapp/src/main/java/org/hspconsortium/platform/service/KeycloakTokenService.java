package org.hspconsortium.platform.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.stream.Collectors;


public class KeycloakTokenService {

    private Log log = LogFactory.getLog(KeycloakTokenService.class);

    @Value("${hspc.platform.keycloak.projectName}")
    private String keycloakProject;

    @Value("${hspc.platform.keycloak.databaseUrl}")
    private String keycloakDatabaseUrl;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private EncryptionService encryptionService;

    @PostConstruct
    private void initKeycloak() throws IOException {
        InputStream keycloakCredentials = null;
        try {
            InputStream encryptedKeycloakCredentials = null;
            encryptedKeycloakCredentials = resourceLoader.getResource("classpath:keycloak-key_" + keycloakProject + ".json").getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(encryptedKeycloakCredentials));
            String encryptedKeycloakCredentialsString = buffer.lines().collect(Collectors.joining("\n"));
            String keycloakCredentialsString = encryptionService.decrypt(encryptedKeycloakCredentialsString);
            keycloakCredentials = new ByteArrayInputStream(keycloakCredentialsString.getBytes());
            encryptedKeycloakCredentials.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
