package com.example.portal.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
  private final Key key;
  private final long expiration;
  public JwtService(@Value("${app.jwt.secret}") String secret,
                    @Value("${app.jwt.expiration}") long expiration) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes()); this.expiration = expiration;
  }
  public String generate(String subject, Map<String,Object> claims){
    return Jwts.builder()
      .setSubject(subject).addClaims(claims)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis()+expiration))
      .signWith(key, SignatureAlgorithm.HS256).compact();
  }
  public Jws<Claims> parse(String token){
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
  }
}
