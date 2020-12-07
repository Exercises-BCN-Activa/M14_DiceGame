package com.dice_game.crud.security;

public enum Role {
	
	BASIC("USER"), CHIEF("ADMIN"), FULL("USER,ADMIN");

	private Role(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}

	private String role;
	
}
