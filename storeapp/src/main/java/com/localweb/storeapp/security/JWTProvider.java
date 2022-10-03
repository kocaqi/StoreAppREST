package com.localweb.storeapp.security;

import com.localweb.storeapp.exception.StoreAPIException;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationinMs;

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationinMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new StoreAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature!");
        } catch (MalformedJwtException ex){
            throw new StoreAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token!");
        } catch (ExpiredJwtException ex){
            throw new StoreAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token!");
        } catch (UnsupportedJwtException ex){
            throw new StoreAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token!");
        } catch (IllegalArgumentException ex){
            throw new StoreAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty!");
        }
    }

}
