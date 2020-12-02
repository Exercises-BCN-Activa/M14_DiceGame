package com.dice_game.crud.security;

public class Constants {

	private Constants() {}

	// Spring Security

	public static final String LOGIN_URL = "/login";
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";

	// JWT

	public static final String JWT_SECRET = "^[(Do || !Do)+(But please, do not try!)*]$";
	public static final String TOKEN_ISSUER = "Fauno Guazina";
	public static final String TOKEN_AUDIENCE = "Dice Game Execise";
	public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
	
}
