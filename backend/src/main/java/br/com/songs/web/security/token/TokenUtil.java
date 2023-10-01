package br.com.songs.web.security.token;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.songs.exception.BadCredentialsCustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;

@Component
public class TokenUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final long JWT_TOKEN_VALIDITY = 60l * 60l * 3l;

	private static final String secret = "bXktMzItY2hhcmFjdGVyLXVsdHJhLXNlY3VyZS1hbmQtdWx0cmEtbG9uZy1zZWNyZXQ=";

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims bodyClaims = null;
		try {
			bodyClaims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
		} catch (SignatureException e) {
			throw new BadCredentialsCustomException(e.getMessage());
		}

		return bodyClaims;
	}

	private boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return generateToken(claims, userDetails.getUsername());
	}

	private String generateToken(Map<String, Object> claims, String subject) {
		SecretKeySpec hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
				SignatureAlgorithm.HS512.getJcaName());
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(hmacKey)
				.compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
