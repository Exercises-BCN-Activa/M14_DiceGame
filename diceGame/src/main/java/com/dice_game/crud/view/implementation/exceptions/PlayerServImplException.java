package com.dice_game.crud.view.implementation.exceptions;

public class PlayerServImplException extends RuntimeException {

	private static final long serialVersionUID = 424786931058427752L;

	private static final String DETAILS = "Exception type Player Service Implementation: ";

	public PlayerServImplException(String message) {
		super(DETAILS.concat(message));
	}
	

}
