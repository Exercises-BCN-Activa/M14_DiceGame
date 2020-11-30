package com.dice_game.crud.security;

import static com.dice_game.crud.security.Constants.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dice_game.crud.dto.Player;
import com.dice_game.crud.security.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;


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
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					player.getUsername(), player.getPassword()));
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		List<String> grantedAuthorities = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		String token = JwtService.createToken(auth.getName(), grantedAuthorities);
		
		response.addHeader(TOKEN_HEADER, TOKEN_PREFIX + token);
	}
	
}
