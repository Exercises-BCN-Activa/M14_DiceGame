package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.Util.isEmpty;
import static com.dice_game.crud.utilities.Util.isValidEmail;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

final class PlayerJsonAspectValidation {
	
	static void verifyIsAbleToSave(PlayerJson playerJson) throws PlayerServImplException {
		PlayerJsonAspectValidation verify = new PlayerJsonAspectValidation(playerJson);
		
		verify.ifNotHaveAllNeededToBeCreatedThrowException();
		
		verify.ifEmailIsInvalidFormatThrowException();
		
	}

	private PlayerJson playerJson;

	private PlayerJsonAspectValidation(PlayerJson playerJson) {
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
		
		String message = "";

		if (isEmpty(playerJson.getEmail()))
			message.concat("Missing E-mail! ");

		if (isEmpty(playerJson.getPassword()))
			message.concat("Missing Password! ");

		if (isEmpty(playerJson.getFirstName()))
			message.concat("Missing First Name! ");

		if (isEmpty(playerJson.getLastName()))
			message.concat("Missing Last Name! ");

		PlayerServImplException.throwsUp(message.trim());
	}

	private void ifEmailIsInvalidFormatThrowException() throws PlayerServImplException {
		
		if (!isValidEmail(playerJson.getEmail()))
			PlayerServImplException.throwsUp("This email has invalid format!");
	}

}
