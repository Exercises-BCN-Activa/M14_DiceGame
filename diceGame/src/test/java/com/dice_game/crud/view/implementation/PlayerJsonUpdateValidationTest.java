package com.dice_game.crud.view.implementation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.security.Role;
import com.dice_game.crud.utilities.Util;

import static com.dice_game.crud.view.implementation.PlayerJsonUpdateValidation.cloneStructureSetWhatIsUpgradeable;

class PlayerJsonUpdateValidationTest {
	
	private Player oldPlayer;
	private Player updatedPlayer;
	private PlayerJson playerJson;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String firstNameUp;
	private String lastNameUp;
	private String emailUp;
	private String passwordUp;

	@BeforeEach
	void setUp() throws Exception {
		firstName = "Fulano";
		lastName = "Ciclano";
		email = "john@somewhere.com";
		password = "senha2020";
		
		oldPlayer = new Player();
		oldPlayer.setId(1l);
		oldPlayer.setRegistration(new Date());
		oldPlayer.setType(Role.BASIC);
		oldPlayer.setRounds(new ArrayList<>());
		oldPlayer.setEmail(email);
		oldPlayer.setPassword(Util.encryptPassword("senha2020"));
		oldPlayer.setFirstName(firstName);
		oldPlayer.setLastName(lastName);
		
		playerJson = new PlayerJson();
		
	}

	@Test
	@DisplayName("Update All fields by Json")
	void testUpdateAllOk() {
		emailUp = "john.some@somewhere.com";
		passwordUp = "senha2021";
		firstNameUp = "John";
		lastNameUp = "Somewhere";
		
		playerJson.setEmail(emailUp);
		playerJson.setPassword(passwordUp);
		playerJson.setFirstName(firstNameUp);
		playerJson.setLastName(lastNameUp);
		
		updatedPlayer = cloneStructureSetWhatIsUpgradeable(oldPlayer, playerJson);
		
		assertAll(
				() -> assertNotNull(updatedPlayer, msgError("NotNull")),
				() -> assertEquals(emailUp, updatedPlayer.getEmail(), msgError("Equals 1")),
				() -> assertEquals(firstNameUp, updatedPlayer.getFirstName(), msgError("Equals 2")),
				() -> assertEquals(lastNameUp, updatedPlayer.getLastName(), msgError("Equals 3")),
				() -> assertTrue(Util.encryptMatches(passwordUp, updatedPlayer.getPassword()), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Update All fields NULL by Json")
	void testUpdateAllNull() {
		
		updatedPlayer = cloneStructureSetWhatIsUpgradeable(oldPlayer, playerJson);
		
		assertAll(
				() -> assertNotNull(updatedPlayer, msgError("NotNull")),
				() -> assertEquals(oldPlayer, updatedPlayer, msgError("Equals 1"))
				);
	}
	
	@Test
	@DisplayName("Update Only Last Name by Json")
	void testUpdateOnlyLastName() {
		lastNameUp = "Somewhere";
		
		playerJson.setLastName(lastNameUp);
		
		updatedPlayer = cloneStructureSetWhatIsUpgradeable(oldPlayer, playerJson);
		
		assertAll(
				() -> assertNotNull(updatedPlayer, msgError("NotNull")),
				() -> assertEquals(email, updatedPlayer.getEmail(), msgError("Equals 1")),
				() -> assertEquals(firstName, updatedPlayer.getFirstName(), msgError("Equals 2")),
				() -> assertEquals(lastNameUp, updatedPlayer.getLastName(), msgError("Equals 3")),
				() -> assertTrue(Util.encryptMatches(password, updatedPlayer.getPassword()), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Update Only First Name by Json")
	void testUpdateOnlyFirstName() {
		firstNameUp = "John";
		
		playerJson.setFirstName(firstNameUp);
		
		updatedPlayer = cloneStructureSetWhatIsUpgradeable(oldPlayer, playerJson);
		
		assertAll(
				() -> assertNotNull(updatedPlayer, msgError("NotNull")),
				() -> assertEquals(email, updatedPlayer.getEmail(), msgError("Equals 1")),
				() -> assertEquals(firstNameUp, updatedPlayer.getFirstName(), msgError("Equals 2")),
				() -> assertEquals(lastName, updatedPlayer.getLastName(), msgError("Equals 3")),
				() -> assertTrue(Util.encryptMatches(password, updatedPlayer.getPassword()), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Update Only Password by Json")
	void testUpdateOnlyPassword() {
		passwordUp = "senha2021";
		
		playerJson.setPassword(passwordUp);
		
		updatedPlayer = cloneStructureSetWhatIsUpgradeable(oldPlayer, playerJson);
		
		assertAll(
				() -> assertNotNull(updatedPlayer, msgError("NotNull")),
				() -> assertEquals(email, updatedPlayer.getEmail(), msgError("Equals 1")),
				() -> assertEquals(firstName, updatedPlayer.getFirstName(), msgError("Equals 2")),
				() -> assertEquals(lastName, updatedPlayer.getLastName(), msgError("Equals 3")),
				() -> assertTrue(Util.encryptMatches(passwordUp, updatedPlayer.getPassword()), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Update Only Email by Json")
	void testUpdateOnlyEmail() {
		emailUp = "john.some@somewhere.com";
		
		playerJson.setEmail(emailUp);
		
		updatedPlayer = cloneStructureSetWhatIsUpgradeable(oldPlayer, playerJson);
		
		assertAll(
				() -> assertNotNull(updatedPlayer, msgError("NotNull")),
				() -> assertEquals(emailUp, updatedPlayer.getEmail(), msgError("Equals 1")),
				() -> assertEquals(firstName, updatedPlayer.getFirstName(), msgError("Equals 2")),
				() -> assertEquals(lastName, updatedPlayer.getLastName(), msgError("Equals 3")),
				() -> assertTrue(Util.encryptMatches(password, updatedPlayer.getPassword()), msgError("True 1"))
				);
	}
	
	
	private String msgError(String input) {
		return String.format("Error in Assert %s", input);
	}
}
