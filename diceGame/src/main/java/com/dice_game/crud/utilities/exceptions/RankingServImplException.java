package com.dice_game.crud.utilities.exceptions;

public class RankingServImplException extends RuntimeException {

	private static final long serialVersionUID = -9182460685126369141L;
	
	private static final String DETAILS = "Exception type Ranking Service Implementation";

	public RankingServImplException(String message) {
		super(String.format("%s : %s!!!", DETAILS, message));
	}
	
	public static void throwsUp(String message) {
		throw new RankingServImplException(message);
	}
}
