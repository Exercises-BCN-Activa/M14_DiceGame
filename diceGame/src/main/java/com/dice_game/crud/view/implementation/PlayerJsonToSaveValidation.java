package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.Util.isEmpty;
import static com.dice_game.crud.utilities.Util.isValidEmail;

import java.util.ArrayList;
import java.util.List;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

final class PlayerJsonToSaveValidation {
	
	static void verifyIsAble(PlayerJson playerJson) throws PlayerServImplException {
		PlayerJsonToSaveValidation verify = new PlayerJsonToSaveValidation(playerJson);
		
		verify.ifNotHaveAllNeededToBeCreatedThrowException();
		
		verify.ifEmailIsInvalidFormatThrowException();
		
	}

	private PlayerJsonToSaveValidation(PlayerJson playerJson) {
		this.playerJson = playerJson;
	}

	private void ifNotHaveAllNeededToBeCreatedThrowException() throws PlayerServImplException {
		if (notHaveAllNeededToBeCreated())
			throwExceptionWithEspecificFlawsOfThis();
	}

	private boolean notHaveAllNeededToBeCreated() {
		return isEmpty(playerJson.getEmail()) || isEmpty(playerJson.getPassword()) 
				|| isEmpty(playerJson.getFirstName()) || isEmpty(playerJson.getLastName());
	}

	private void throwExceptionWithEspecificFlawsOfThis() throws PlayerServImplException {
		
		List<String> message = new ArrayList<>();

		if (isEmpty(playerJson.getEmail()))
			message.add("Missing E-mail!");

		if (isEmpty(playerJson.getPassword()))
			message.add("Missing Password!");

		if (isEmpty(playerJson.getFirstName()))
			message.add("Missing First Name!");

		if (isEmpty(playerJson.getLastName()))
			message.add("Missing Last Name!");

		PlayerServImplException.throwsUp(String.join(" ", message));
	}

	private void ifEmailIsInvalidFormatThrowException() throws PlayerServImplException {
		
		if (!isValidEmail(playerJson.getEmail()))
			PlayerServImplException.throwsUp("This email has invalid format!");
	}
	
	private final PlayerJson playerJson;

}
