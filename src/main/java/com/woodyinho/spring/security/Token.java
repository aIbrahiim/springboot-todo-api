package com.woodyinho.spring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Token {
    private final String CLAIMS_SUBJECT = "sub";

    private final String CLAIMS_CREATED = "created";

    @Value("${jwt.expiration}")
    private long TOKEN_VALIDITY = 604800;

    @Value("${jwt.secret}")
    private String TOKEN_SECRET;

    public String generateToken(UserDetails userDetails){
        /* expiration
        * sign
        * compact*/

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_SUBJECT, userDetails.getUsername());
        claims.put(CLAIMS_CREATED, new Date());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExperationDate())
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
    }



    private Claims getClaims(String token){
        Claims claims;
        try {
              claims = Jwts.parser().setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();

        }catch (Exception e){
            return null;
        }
        return claims;
    }

    public String getUserNameFromToken(String token){
        try{

            Claims claims = getClaims(token);
            return claims.getSubject();
        }catch (Exception ex){
            return null;
        }
    }

    private Date generateExperationDate() {
        return new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = getUserNameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}
