package s1mple.dlowji.ffms_refactor.helper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import s1mple.dlowji.ffms_refactor.security.userprincipal.UserPrincipal;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;


@Component
public class JwtHelper {
	private final Key key;
	private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);

	public JwtHelper(@Value("${jwt.secret}") String privateKey) {
		key = Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8));
	}

	public String createToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		return Jwts.builder().setSubject(userPrincipal.getUsername()).setExpiration(Date.from(Instant.now().plus(60, ChronoUnit.MINUTES))).signWith(key).compact();
	}

	public String createToken(String sub, Map<String, Object> claims) {
		return Jwts.builder()
		.addClaims(claims)
		.setExpiration(Date.from(Instant.now().plus(5, ChronoUnit.MINUTES)))
		.signWith(key)
		.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException expiredJwtException) {
			logger.error("Expired jwt token: " + expiredJwtException.getMessage());
		} catch (MalformedJwtException malformedJwtException) {
			logger.error("Invalid jwt malformed " + malformedJwtException.getMessage());
		} catch (SignatureException signatureException) {
			logger.error("Invalid jwt token: " + signatureException.getMessage());
		} catch (IllegalArgumentException illegalArgumentException) {
			logger.error("Invalid jwt token: " + illegalArgumentException.getMessage());
		} catch (UnsupportedJwtException unsupportedJwtException) {
			logger.error("Invalid jwt token: " + unsupportedJwtException.getMessage());
		}
		return false;
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	public Map<String, Object> parseClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
