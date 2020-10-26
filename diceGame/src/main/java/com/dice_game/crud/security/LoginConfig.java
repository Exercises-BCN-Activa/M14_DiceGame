package com.dice_game.crud.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginConfig extends AbstractAuthenticationProcessingFilter {

	public LoginConfig(String pathURL, AuthenticationManager authenticationManager) {
		
		super(new AntPathRequestMatcher(pathURL));
		
		setAuthenticationManager(authenticationManager);
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		//captures the request body in JSON format {"username":"ask", "password":"123"}
		InputStream body = request.getInputStream();
		
		//map the USER class
		User user = new ObjectMapper().readValue(body, User.class);
		
		//Compare data sent with data built in SecurityConfig
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						user.getName(),
						user.getPassword(),
						Collections.emptyList()));
	}
	

}
