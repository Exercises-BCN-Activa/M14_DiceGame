package com.dice_game.crud.security.jwt;

import static com.dice_game.crud.security.Constants.TOKEN_HEADER;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		try {

			String token = request.getHeader(TOKEN_HEADER);

			JwtService.isValid(token);

			List<GrantedAuthority> authorities = JwtService.extractRoles(token).stream()
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

			UsernamePasswordAuthenticationToken loggedUser = new UsernamePasswordAuthenticationToken(
																JwtService.extractUsername(token), null, authorities);

			SecurityContextHolder.getContext().setAuthentication(loggedUser);

			filterChain.doFilter(request, response);

		} catch (Exception e) {

			response.addHeader("ERROR", e.getMessage());
			response.sendError(403);
			System.out.println(new Date() + " => " + e.getMessage());
		}
	}

}
