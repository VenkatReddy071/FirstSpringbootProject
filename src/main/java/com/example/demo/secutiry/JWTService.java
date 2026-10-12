package com.example.demo.secutiry;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.entities.UsersEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {

	 private final SecretKey key;
	 private final long expiration;

	    public JWTService(
	            @Value("${security.jwt.secret}") String secretKey,
	            @Value("${security.jwt.expiration-ms}") long expiration) {

	        this.key = Keys.hmacShaKeyFor(
	                Decoders.BASE64.decode(secretKey));

	        this.expiration = expiration;
	    }
	    
	 public String generateToken(UsersEntity entity) {
		 Date now=new Date();
		 
		 return Jwts.builder()
				 .subject(entity.getId().toString())
				 .claims(getClaims(entity))
				 .issuedAt(now)
				 .expiration(new Date(now.getTime()+expiration))
				 .signWith(key)
				 .compact();
	 }
	 public Map<String,String> getClaims(UsersEntity entity){
		 Map<String,String> map=new HashMap<>();
		 map.put("email", entity.getEmail());
		 map.put("userRoleName", entity.getUserRole().getRoleName().toString());
		 map.put("userRoleId", entity.getUserRole().getId().toString());
		 map.put("organizationId", entity.getOrganization().getId().toString());
		 return map;
	 }
	 public Integer getUserId(String token) {
	        return Integer.valueOf(
	                extractClaims(token).getSubject());
	    }
	 public Integer getOrganizationId(String token) {
		 return Integer.valueOf(extractClaims(token).get("organizationId",String.class));
	 }
	 public Integer getRoleId(String token) {
		 return Integer.valueOf(extractClaims(token).get("userRoleId",String.class));
	 }
	 public String getRoleName(String token) {
		 return extractClaims(token).get("userRoleName",String.class);
	 }
	  public String getEmail(String token) {
	        return extractClaims(token).get("email", String.class);
	  }
	 public Claims extractClaims(String token) {
		 return Jwts.parser()
				 .verifyWith(key)
				 .build()
				 .parseSignedClaims(token)
				 .getPayload();
	 }
	 
	 public boolean isValid(String token,UserDetails userDetails) {
		 
		 String email=getEmail(token);
		 boolean expired=isExpired(token);
		 
		 return !expired && email.equals(userDetails.getUsername());
	 }
	 public boolean isExpired(String token) {
		    Date currentDate = new Date();

		    return extractClaims(token)
		            .getExpiration()
		            .before(currentDate);
		}
	
}
