package com.dice_game.crud.security;

import static com.dice_game.crud.security.Constants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dice_game.crud.dto.Player;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			Player player = new ObjectMapper().readValue(request.getInputStream(), Player.class);
			

			List<GrantedAuthority> authorities = new ArrayList<>();
			
			for (String role : player.getRoles().replaceAll("( )+", "").split(",")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			}
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					player.getUsername(), player.getPassword(), authorities));
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUER)
				.setSubject(((User)auth.getPrincipal()).getUsername())
				.setAudience((((User)auth.getPrincipal()).getAuthorities()).toString())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SS_KEY).compact();
		response.addHeader(HEADER_KEY, TOKEN_PREFIX + token);
	}
	
}
