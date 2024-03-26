package com.alibou.alibou.security.service.impl;

import com.alibou.alibou.Model.User;
import com.alibou.alibou.security.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    public String generateToken(User userDetails){

        int user_id = userDetails.getUser_id();

        return Jwts.builder().setSubject(userDetails.getUsername())
                .claim("user_id" , user_id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSiginKey() , SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSiginKey() , SignatureAlgorithm.HS256)
                .compact();
    }
    private <T> T extractClaim(String token , Function<Claims , T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    private Key getSiginKey(){
        byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D9703373367639792442645948404D6351");
        return Keys.hmacShaKeyFor(key);
    }

    public String extractUserName(String token){
        return extractClaim(token , Claims::getSubject);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token , Claims::getExpiration).before(new Date());
    }

}
