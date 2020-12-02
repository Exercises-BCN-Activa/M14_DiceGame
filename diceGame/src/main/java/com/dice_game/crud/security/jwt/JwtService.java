package com.dice_game.crud.security.jwt;

import static com.dice_game.crud.security.Constants.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;

public final class JwtService {

	public static boolean isValid(String token) {
		if (token == null) {
			throw new JwtException("This Token is null");
		}
		else if (!token.startsWith(TOKEN_PREFIX) && token.split("\\.").length != 3) {
			throw new JwtException("This Token is not ours");
		}
		else if (decodeToken(token).getExpiration().before(new Date())) {
			throw new JwtException("This Token has Expired");
		} else {
			return true;
		}
	}

	public static String extractUsername(String token) {
		return decodeToken(token).getSubject();
	}

	@SuppressWarnings("unchecked")
	public static List<String> extractRoles(String token) {
		return (List<String>) decodeToken(token).get("roles");
	}

	public static String createToken(String user, List<String> roles) {
		return Jwts.builder().setIssuedAt(new Date()).setIssuer(TOKEN_ISSUER)
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.setSubject(user).claim("roles", roles).setAudience(TOKEN_AUDIENCE)
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
	}

	private static Claims decodeToken(String token) {

		try {

			return Jwts.parser().setSigningKey(JWT_SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

		} catch (Exception exception) {
			throw new JwtException("Security JWT has a problem: " + exception.getMessage());
		}
	}

}
