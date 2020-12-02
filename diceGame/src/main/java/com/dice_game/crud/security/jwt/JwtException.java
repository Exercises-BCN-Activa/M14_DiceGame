package com.dice_game.crud.security.jwt;

public class JwtException extends RuntimeException {

	private static final long serialVersionUID = -5577446446709001508L;
	
	private static final String DETAILS = "exception type JWT";

    public JwtException(String detail) {
        super(DETAILS + ": -> " + detail);
    }
    
}
