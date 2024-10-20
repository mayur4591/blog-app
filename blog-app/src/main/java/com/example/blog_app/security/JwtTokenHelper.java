package com.example.blog_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    private String secret = "jwtsecretkeyjwtsecretkeyjwtsecretkeyjwtsecretkeyjwtsecretkey";

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+JWT_TOKEN_VALIDITY))
                .signWith(key())
                .compact();

    }
    private String doGenerateToken(Map<String,Object> claims,String subject) {
        return Jwts.builder().claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*100))
                .signWith(key())
                .compact();
    }

    public Boolean validateToken(String token,UserDetails userDetails){
        final String userName = getUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public String getUsernameFromToken(String token){
        return Jwts.parser().
                verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key= Keys.hmacShaKeyFor(keyBytes);
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        final Date expitation = getExpirationDateFromToken(token);
        return expitation.before(new Date());
    }


}
