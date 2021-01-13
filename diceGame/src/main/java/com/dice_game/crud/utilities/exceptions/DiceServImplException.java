package com.dice_game.crud.utilities.exceptions;

public class DiceServImplException extends RuntimeException {

	private static final long serialVersionUID = 5263082090210983393L;

	private static final String DETAILS = "Exception type Dice Service Implementation";

	public DiceServImplException(String message) {
		super(String.format("%s : %s!!!", DETAILS, message));
	}
	
	public static void throwsUp(String message) {
		throw new DiceServImplException(message);
	}

}
