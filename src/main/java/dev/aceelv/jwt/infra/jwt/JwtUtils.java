package dev.aceelv.jwt.infra.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.aceelv.jwt.model.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    // Generate token acces
    public String generateTokenAcces(User user) {
        Date expirationDate = new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration));
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(expirationDate)
                    .withSubject(user.getUsername())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Verify token
    public String verifyTokenAcces(String token) {
        DecodedJWT decodedJWT;
        JWTVerifier verifier;

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            verifier = JWT.require(algorithm)
                    .build();

            decodedJWT = verifier.verify(token);

            if(decodedJWT != null) {
                String subject = decodedJWT.getSubject();
                if(subject != null) {
                    return subject;
                }
            }
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Verifier inválido");

    }


}
