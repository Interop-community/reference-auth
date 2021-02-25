package org.logicahealth.platform.service;

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

public class FirebaseTokenService {

    private Log log = LogFactory.getLog(FirebaseTokenService.class);

    private FirebaseApp firebaseApp;

    @Value("${hspc.platform.firebase.projectName}")
    private String firebaseProject;

    @Value("${hspc.platform.firebase.databaseUrl}")
    private String firebaseDatabaseUrl;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private EncryptionService encryptionService;

    @PostConstruct
    private void initFirebase() throws IOException {
        InputStream firebaseCredentials = null;
        try {
            InputStream encryptedFirebaseCredentials = null;
            encryptedFirebaseCredentials = resourceLoader.getResource("classpath:firebase-key_" + firebaseProject + ".json").getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(encryptedFirebaseCredentials));
            String encryptedFirebaseCredentialsString = buffer.lines().collect(Collectors.joining("\n"));
            String firebaseCredentialsString = encryptionService.decrypt(encryptedFirebaseCredentialsString);
            firebaseCredentials = new ByteArrayInputStream(firebaseCredentialsString.getBytes());
            encryptedFirebaseCredentials.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(firebaseCredentials))
                .setDatabaseUrl(firebaseDatabaseUrl)
                .build();

        firebaseApp = FirebaseApp.initializeApp(options);
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
