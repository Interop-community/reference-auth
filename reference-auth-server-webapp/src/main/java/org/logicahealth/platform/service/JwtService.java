package org.logicahealth.platform.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${hspc.platform.jwt.key}")
    private String jwtKey;

    @Value("${hspc.platform.jwt.signatureAlgorithm}")
    private String signatureAlgorithm;

    public boolean jwtIsValid(String jwtString){
        try{
            Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(jwtString);
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    public String usernameFromJwt(String compactJws) {
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(compactJws);

            //OK, we can trust this JWT
            return jws.getBody().getSubject();

        } catch (SignatureException e) {
            return null;
        }
    }
}
