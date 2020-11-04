package com.dice_game.crud.security;

class Constants {

	private Constants() {}

	// Spring Security

	static final String LOGIN_URL = "/login";
	static final String HEADER_KEY = "Authorization";
	static final String TOKEN_PREFIX = "Bearer ";

	// JWT

	static final String ISSUER = "Fauno Guazina";
	static final String SS_KEY = "it WaS a LoT of WoRK";
	static final long EXPIRATION_TIME = 864_000_000; // 10 day
	
}
