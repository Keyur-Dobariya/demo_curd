package com.demoproject.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final String SECRET_KEY = "r8gfZp4K5hJ+5E7DjVsyz+GJjzSseRfpT3Y0P1a6VfQ=";  // Same secret key used for signing

    // Method to generate a token
    public String generateToken(String username, String password) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withIssuer("auth0")
                .withSubject(username)
                .withSubject(password)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour expiration
                .sign(algorithm);
    }

    // Method to validate a token
    public boolean validateToken(String token) {
        try {
            // Specify the algorithm used for verification
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            // Create the JWTVerifier instance
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0") // Ensure the same issuer is used
                    .build(); // Reusable verifier instance

            // Verify the token
            DecodedJWT jwt = verifier.verify(token);
            return true; // Token is valid

        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            System.out.println("Invalid token: " + exception.getMessage());
            return false; // Token is invalid
        }
    }

    // Optional: Get user details (like username) from a validated token
    public String getUsernameFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }
}
