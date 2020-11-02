package com.dice_game.crud.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpS) throws Exception {
		httpS.csrf().disable()
			.authorizeRequests()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
			.and()
				.addFilterBefore(new LoginConfig("/login", authenticationManager()),
		                 UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JwtFilter(),
						UsernamePasswordAuthenticationFilter.class)
			;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder amb) throws Exception {
		//creating accounts by default
		amb.inMemoryAuthentication()
				.withUser("fauno")
				.password("{noop}puc")
				.roles("ADMIN");
		
		amb.inMemoryAuthentication()
				.withUser("user")
				.password("{noop}usr")
				.roles("USER");
	}
}
