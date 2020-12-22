package com.dice_game.crud.view.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

class PlayerJsonToSaveValidationTest {
	
	private PlayerJson pJsonWithoutAllNull;
	private PlayerJson pJsonWithoutAllEmpty;
	private PlayerJson pJsonWithoutEmail;
	private PlayerJson pJsonWithoutPassword;
	private PlayerJson pJsonWithoutFirstName;
	private PlayerJson pJsonWithoutLastName;
	private PlayerJson pJsonWithAllButInvalidEmail;
	private PlayerJson pJsonWithAllOk;
	private String firstName;
	private String lastName;
	private String email;
	private String emailInvalid;
	private String password;

	@BeforeEach
	void setUp() throws Exception {
		pJsonWithoutAllNull = new PlayerJson();
		pJsonWithoutAllNull.setFirstName(null);
		pJsonWithoutAllNull.setLastName(null);
		pJsonWithoutAllNull.setPassword(null);
		pJsonWithoutAllNull.setEmail(null);
		
		pJsonWithoutAllEmpty = new PlayerJson();
		pJsonWithoutAllEmpty.setFirstName("");
		pJsonWithoutAllEmpty.setLastName("");
		pJsonWithoutAllEmpty.setPassword("");
		pJsonWithoutAllEmpty.setEmail("");
		
		firstName = "Fulano";
		lastName = "Ciclano";
		email = "john@somewhere.com";
		emailInvalid = ".@somewhere.com";
		password = "senha2020";
		
		pJsonWithoutEmail = new PlayerJson();
		pJsonWithoutEmail.setFirstName(firstName);
		pJsonWithoutEmail.setLastName(lastName);
		pJsonWithoutEmail.setPassword(password);
		
		pJsonWithoutPassword = new PlayerJson();
		pJsonWithoutPassword.setFirstName(firstName);
		pJsonWithoutPassword.setLastName(lastName);
		pJsonWithoutPassword.setEmail(email);
		
		pJsonWithoutFirstName = new PlayerJson();
		pJsonWithoutFirstName.setPassword(password);
		pJsonWithoutFirstName.setLastName(lastName);
		pJsonWithoutFirstName.setEmail(email);
		
		pJsonWithoutLastName = new PlayerJson();
		pJsonWithoutLastName.setPassword(password);
		pJsonWithoutLastName.setFirstName(firstName);
		pJsonWithoutLastName.setEmail(email);
		
		pJsonWithAllButInvalidEmail = new PlayerJson();
		pJsonWithAllButInvalidEmail.setFirstName(firstName);
		pJsonWithAllButInvalidEmail.setLastName(lastName);
		pJsonWithAllButInvalidEmail.setPassword(password);
		pJsonWithAllButInvalidEmail.setEmail(emailInvalid);
		
		pJsonWithAllOk = new PlayerJson();
		pJsonWithAllOk.setFirstName(firstName);
		pJsonWithAllOk.setLastName(lastName);
		pJsonWithAllOk.setPassword(password);
		pJsonWithAllOk.setEmail(email);
		
	}

	@Test
	@DisplayName("Should Throw Exception")
	void testVerifyIsAbleToSaveThrow() {
		
		assertAll(
				() -> assertThrows(PlayerServImplException.class, 
						() -> PlayerJsonToSaveValidation.verifyIsAble(pJsonWithoutAllNull), msgError("Throws 1")),
				() -> assertThrows(PlayerServImplException.class, 
						() -> PlayerJsonToSaveValidation.verifyIsAble(pJsonWithoutAllEmpty), msgError("Throws 2")),
				() -> assertThrows(PlayerServImplException.class, 
						() -> PlayerJsonToSaveValidation.verifyIsAble(pJsonWithoutEmail), msgError("Throws 3")),
				() -> assertThrows(PlayerServImplException.class, 
						() -> PlayerJsonToSaveValidation.verifyIsAble(pJsonWithoutPassword), msgError("Throws 4")),
				() -> assertThrows(PlayerServImplException.class, 
						() -> PlayerJsonToSaveValidation.verifyIsAble(pJsonWithoutFirstName), msgError("Throws 5")),
				() -> assertThrows(PlayerServImplException.class, 
						() -> PlayerJsonToSaveValidation.verifyIsAble(pJsonWithoutLastName), msgError("Throws 6")),
				() -> assertThrows(PlayerServImplException.class, 
						() -> PlayerJsonToSaveValidation.verifyIsAble(pJsonWithAllButInvalidEmail), msgError("Throws 7"))
				);

	}

	@Test
	@DisplayName("Should NOT Throw Exception")
	void testVerifyIsAbleToSaveDoesNotThrow() {

		assertDoesNotThrow(() -> PlayerJsonToSaveValidation.verifyIsAble(pJsonWithAllOk),
				msgError("Does Not Throw 9"));

	}
	
	private String msgError(String input) {
		return String.format("Error in Assert %s", input);
	}

}
