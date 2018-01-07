package com.logicq.mlm.common.helper;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import com.logicq.mlm.service.security.UserService;
import com.logicq.mlm.vo.LoginVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public final class TokenHandler {
	private static final String CLAIM_KEY_CREATED = "created";
	private final String secret;
	private final UserService userService;
	private final static Long expiration = new Long(100000);

	public TokenHandler(String secret, UserService userService) {
		this.secret = secret;//Base64.encode(secret.getBytes());
		this.userService = userService;
	}

	public LoginVO parseUserFromToken(String token) {
		String username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
		return userService.loadUserByUsername(username);
	}

	public String createTokenForUser(LoginVO user) {
		Date now = new Date();
		return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(user.getUsername()).setIssuedAt(now)
				.setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public static String parseMobileFromToken(String token) {
		return Jwts.parser().setSigningKey("mobile").parseClaimsJws(token).getBody().getSubject();
	}
	
	public static String createTokenForOTP(String mobilenumber) {
		Date now = new Date();
		return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(mobilenumber).setIssuedAt(now)
				.setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, "mobile").compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		LoginVO user = (LoginVO) userDetails;
		final String username = getUsernameFromToken(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}

	private static Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

}
