package com.dice_game.crud.security.jwt;

import static com.dice_game.crud.security.Constants.TOKEN_HEADER;
import static com.dice_game.crud.security.Constants.TOKEN_PREFIX;

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

import com.dice_game.crud.model.dto.PlayerJson;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			PlayerJson player = new ObjectMapper().readValue(request.getInputStream(), PlayerJson.class);
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					player.getEmail(), player.getPassword()));
			
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
