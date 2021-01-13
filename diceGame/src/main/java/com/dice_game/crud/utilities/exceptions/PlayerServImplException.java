package com.dice_game.crud.utilities.exceptions;

public class PlayerServImplException extends RuntimeException {

	private static final long serialVersionUID = 424786931058427752L;

	private static final String DETAILS = "Exception type Player Service Implementation";

	public PlayerServImplException(String message) {
		super(String.format("%s : %s!!!", DETAILS, message));
	}
	
	public static void throwsUp(String message) {
		throw new PlayerServImplException(message);
	}
}
