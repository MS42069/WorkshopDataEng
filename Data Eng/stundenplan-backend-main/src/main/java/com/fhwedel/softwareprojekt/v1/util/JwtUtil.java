package com.fhwedel.softwareprojekt.v1.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fhwedel.softwareprojekt.v1.model.User;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Utility class for handling JSON Web Tokens (JWT). */
@Component
public class JwtUtil {
    /** The secret key used for signing and verifying JWTs. */
    @Value("${custom.auth.access-token.secret}")
    private String secret;

    /**
     * Generates a JWT token for the given user and expiration time.
     *
     * @param user The user for whom the token is generated.
     * @param expiresAt The expiration time of the token.
     * @return The JWT token as a string.
     */
    public String generateJwtToken(User user, Instant expiresAt) {
        Algorithm algorithm = Algorithm.HMAC512(secret);

        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("displayname", user.getDisplayName())
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    /**
     * Retrieves the username from a JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     * @throws JWTVerificationException If there is an issue verifying the token.
     */
    public String getUsernameFromToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token).getSubject();
    }

    /**
     * Retrieves the display name from a JWT token.
     *
     * @param token The JWT token.
     * @return The display name extracted from the token.
     * @throws JWTVerificationException If there is an issue verifying the token.
     */
    public String getDisplaynameFromToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token).getClaim("displayname").asString();
    }
}
