package com.project.flightmanagementsystem.jwt;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil {

    @Value("${secret.key}")
    private String secretKey;

     //Step-1 Generate Token
    
	public String generateToken(String subject) {
        return  Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .issuer("Raj")
                .expiration(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(15)))
                .issuedAt(new Date(System.currentTimeMillis()))
                .compact();
    }

    // Step-2 Read Claims
    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token).getBody();
    }

    // Step-3 Read Username
    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    // step-4 Read Token Exp Date
    public Date getExpiration(String token){
        return getClaims(token).getExpiration();
    }


    // step-5 Check for Token Expiration
    public Boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }


    // step-6 Check for Whether token is valid and check exp time
    public boolean validateToken(String token, String username) {
        var tokenUsername=getSubject(token);
        return tokenUsername.equals(username) && !isTokenExpired(token);
    }




}



