package com.drkrishi.usermanagement.jwtsecurity.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.drkrishi.usermanagement.jwtsecurity.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {

	public String generate(JwtUser jwtUser) {

		Claims claims = Jwts.claims().setSubject(jwtUser.getUserName());
		claims.put("userId", String.valueOf(jwtUser.getId()));
		claims.put("role", jwtUser.getRole());

		Date now = new Date();
		Date validity = new Date(now.getTime() + 60000); // miliseconds

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
				.signWith(SignatureAlgorithm.HS512, "youtube").compact();
	}
}
