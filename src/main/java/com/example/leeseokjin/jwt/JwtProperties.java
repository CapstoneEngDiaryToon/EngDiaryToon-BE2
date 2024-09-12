package com.example.leeseokjin.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    //private SignatureAlgorithm signatureAlgorithm;
    private String signatureAlgorithm;
    private Long tokenExpired;
    public SignatureAlgorithm getSignatureAlgorithm() {
        if (signatureAlgorithm == null) {
            throw new IllegalArgumentException("Signature algorithm cannot be null");
        }
        return SignatureAlgorithm.forName(signatureAlgorithm);
    }
}
