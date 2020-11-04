package com.dice_game.crud.security;

import static com.dice_game.crud.security.Constants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
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

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter implements Filter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HEADER_KEY);
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_KEY);
		if (token != null) {
			String user = Jwts.parser()
						.setSigningKey(SS_KEY)
						.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
						.getBody()
						.getSubject();
			String roles = Jwts.parser()
					.setSigningKey(SS_KEY)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody().getAudience().replaceAll("\\W+", "");
			
			@SuppressWarnings("serial")
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>() 
										{{add(new SimpleGrantedAuthority(roles));}};

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, authorities);
			}
			
			return null;
		}
		return null;
	}

}
