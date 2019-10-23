package org.hspconsortium.platform.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
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

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private EncryptionService encryptionService;

    @PostConstruct
    private void initKeycloak() throws IOException {
        InputStream keycloakCredentials = null;
        try {
            InputStream encryptedKeycloakCredentials = resourceLoader.getResource("classpath: keycloak.json").getInputStream(); // TODO: ASK about the classpath
            BufferedReader buffer = new BufferedReader(new InputStreamReader(encryptedKeycloakCredentials));
            String encryptedKeycloakCredentialsString = buffer.lines().collect(Collectors.joining("\n"));
            String keycloakCredentialsString = encryptionService.decryptKeycloak(encryptedKeycloakCredentialsString);
            keycloakCredentials = new ByteArrayInputStream(keycloakCredentialsString.getBytes());
            encryptedKeycloakCredentials.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FirebaseToken validateToken(String firebaseJwt) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifyIdToken(firebaseJwt);
    }

    public synchronized UserRecord getUserProfileInfo(String email) {
        try {
            return FirebaseAuth.getInstance().getUserByEmail(email);
        } catch (FirebaseAuthException e) {
            return null;
        }
    }
}
