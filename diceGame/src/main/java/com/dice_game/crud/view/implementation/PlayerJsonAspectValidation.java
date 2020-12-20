package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.Util.isEmpty;
import static com.dice_game.crud.utilities.Util.isValidEmail;
import static com.dice_game.crud.view.implementation.PlayerComponent.exists;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

final class PlayerJsonAspectValidation {

	private PlayerJson playerJson;

	PlayerJsonAspectValidation(PlayerJson playerJson) {
		this.playerJson = playerJson;
	}
	
	static void verifyIsAbleToSave(PlayerJson playerJson) {
		PlayerJsonAspectValidation verify = new PlayerJsonAspectValidation(playerJson);

		verify.ifNotHaveAllNeededToBeCreatedThrowException();
		
		verify.ifEmailIsInvalidFormatThrowException();

		verify.ifEmailIsAlreadyRegisteredThrowException();
	}

	void ifNotHaveAllNeededToBeCreatedThrowException() throws PlayerServImplException {
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

	void ifEmailIsInvalidFormatThrowException() throws PlayerServImplException {
		
		if (!isValidEmail(playerJson.getEmail()))
			PlayerServImplException.throwsUp("This email has invalid format!");
	}

	void ifEmailIsAlreadyRegisteredThrowException() throws PlayerServImplException {
		
		if (exists(playerJson))
			PlayerServImplException.throwsUp("This email is already registered!");
	}

}
