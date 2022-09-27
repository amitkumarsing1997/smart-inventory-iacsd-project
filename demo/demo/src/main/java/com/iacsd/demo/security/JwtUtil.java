package com.iacsd.demo.security;

import com.iacsd.demo.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtil {

    private final String TYPE = "type";
    private final String ACCESS_TOKEN = "accessToken";
    private final String REFRESH_TOKEN = "refreshToken";
    private final String USERNAME = "username";
    private final String UID = "uid";


    private final SecretKey KEY = Keys.hmacShaKeyFor("smart-stock-welcome-to-new-world-of-stock-inventory-management".getBytes());
    private final JwtParser PARSER = Jwts.parserBuilder().setSigningKey(KEY).build();

    private String getTokenFromRequest() {
       String bearer = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
       return bearer.substring(7);
    }


    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)))
                .signWith(KEY).compact();
    }

    public String generateToken(AppUser appUser) {
        Map<String, Object> claims = new LinkedHashMap<>();
        // pass and put extra details in token
        claims.put(TYPE, ACCESS_TOKEN);
        claims.put(USERNAME, appUser.getUsername());
        claims.put(UID, appUser.getUid());
        return createToken(claims, appUser.getUsername());
    }

    public String generateRefreshToken(AppUser appUser) {
        return Jwts.builder().setClaims(Map.of(TYPE, REFRESH_TOKEN)).setSubject(appUser.getUsername()).setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(90, ChronoUnit.DAYS)))
                .signWith(KEY).compact();
    }

    private Claims getAllClaims(String token) {
        return PARSER.parseClaimsJws(token).getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(Date.from(Instant.now()));
    }

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public String getUid(String token) {
        return getClaim(token, claims -> claims.get(UID, String.class));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String getTokenType(String token) {
        return this.getClaim(token, claims -> claims.get(TYPE, String.class));
    }

    public boolean isAccessToken(String token) {
        return this.getTokenType(token).equals(ACCESS_TOKEN);
    }

    public boolean isRefreshToken(String token) {
        return this.getTokenType(token).equals(REFRESH_TOKEN);
    }

    public String getUsername() {
        return this.getUsername(this.getTokenFromRequest());
    }

    public String getUid() { return this.getUid(this.getTokenFromRequest());}
}
