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
		httpS
			.authorizeRequests()
				.anyRequest()
				.authenticated()
				.and()
			.formLogin().permitAll()
				.and()
			.addFilterBefore(new LoginConfig("/api/", authenticationManager()),
		                 UsernamePasswordAuthenticationFilter.class)
			.csrf().disable();
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
