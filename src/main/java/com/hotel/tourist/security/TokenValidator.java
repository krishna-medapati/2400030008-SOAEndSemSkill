package com.hotel.tourist.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.tourist.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;

@Component
public class TokenValidator {

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${app.security.jwt-secret}")
    private String secret;

    public void validate(String token) {
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Invalid or missing request token");
        }

        String jwt = token.trim();
        if (jwt.regionMatches(true, 0, "Bearer ", 0, 7)) {
            jwt = jwt.substring(7).trim();
        }

        String[] parts = jwt.split("\\.");
        if (parts.length != 3) {
            throw new InvalidTokenException("Invalid or missing request token");
        }

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] expected = mac.doFinal((parts[0] + "." + parts[1]).getBytes(StandardCharsets.UTF_8));
            byte[] actual = Base64.getUrlDecoder().decode(parts[2]);

            if (!MessageDigest.isEqual(expected, actual)) {
                throw new InvalidTokenException("Invalid token signature");
            }

            JsonNode payload = mapper.readTree(Base64.getUrlDecoder().decode(parts[1]));
            if (payload.has("exp") && payload.get("exp").asLong() < Instant.now().getEpochSecond()) {
                throw new InvalidTokenException("Token expired");
            }
        } catch (InvalidTokenException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid or missing request token");
        }
    }
}
