package com.example.twitterwebapp.config.security.jwt;

import com.example.twitterwebapp.config.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Date;

@Log4j2
@Component
public class JwtTokenUtil {
    @Value("${jwt.jwtSecret}")
    private String jwtSecret;
    @Value("${jwt.jwtIssuer}")
    private String jwtIssuer;
    @Value("${jwt.expirationTime}")
    private Long expirationTime;

    private final Clock clock;

    public JwtTokenUtil(Clock clock) {
        this.clock = clock;
    }

    public String generateAccessToken(UserDetailsImpl user) {
        return Jwts.builder()
                   .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
                   .setIssuer(jwtIssuer)
                   .setIssuedAt(Date.from(clock.instant()))
                   .setExpiration(new Date(clock.millis() + expirationTime))
                   .signWith(SignatureAlgorithm.HS512, jwtSecret)
                   .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}
