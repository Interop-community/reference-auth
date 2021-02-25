package org.logicahealth.platform.service;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;

public class EncryptionService {

    @Value("${hspc.platform.jwt.key}")
    private String password;

    @Value("${hspc.platform.keycloak.jwt.key}")
    private String passwordKeycloak;

    public String decrypt(String encryptedText) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(password);
        return textEncryptor.decrypt(encryptedText);
    }

    public String decryptKeycloak(String encryptedText) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(passwordKeycloak);
        return textEncryptor.decrypt(encryptedText);
    }
}
