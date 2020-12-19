package com.dice_game.crud.model.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dice_game.crud.security.Role;

class DiceTest {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Date registration;
	private String password;
	private Player player;
	private Dice winner;
	private Dice loser;
	private Dice dice;
	private DiceJson diceJson;

	@BeforeEach
	void setUp() throws Exception {
		id = 1l;
		firstName = "Fulano";
		lastName = "Ciclano";
		email = "fulano@cliclano.com";
		password = "senha2020";
		registration = new Date();

		player = new Player();
		player.setId(id);
		player.setEmail(email);
		player.setFirstName(firstName);
		player.setLastName(lastName);
		player.setStatus();
		player.setRegistration(registration);
		player.setType(Role.BASIC);
		player.setPassword(password);
		
		dice = Dice.newRound(player);
		
		winner = Dice.newRound(player);
		winner.setValue1(5);
		winner.setValue2(2);
		winner.setId(id);
		winner.setRegistration(registration);
		winner.setPlayer(player);
		
		loser = Dice.newRound(player);
		loser.setValue1(3);
		loser.setValue2(1);
		
		diceJson = new DiceJson();
		diceJson.setId(id);
		diceJson.setRegistration(registration);
		diceJson.setStatus(winner.isWon());
		diceJson.setValue1(5);
		diceJson.setValue2(2);
		
	}

	@Test
	@DisplayName("Getters/Setters Dice")
	public void testGettersSettersDice() {
		
		assertAll(
				() -> assertEquals(null, dice.getId(), msgError("nullError")),
				() -> assertEquals(null, dice.getRegistration(), msgError("nullError")),
				() -> assertTrue(dice.getPlayer().equals(player), msgError("Player")),
				() -> assertTrue(dice.getValue1() >= 1 && dice.getValue1() <= 6, msgError("rangeError")),
				() -> assertTrue(dice.getValue2() >= 1 && dice.getValue2() <= 6, msgError("rangeError")),
				() -> assertTrue(dice.isWon() || !dice.isWon(), msgError("wonError")),
				
				() -> assertEquals(5, winner.getValue1(), msgError("Value 1")),
				() -> assertEquals(2, winner.getValue2(), msgError("value 2")),
				() -> assertEquals(1, winner.getId(), msgError("Id")),
				() -> assertEquals(registration, winner.getRegistration(), msgError("Registration date")),
				() -> assertEquals(player, winner.getPlayer(), msgError("Player")),
				() -> assertTrue(winner.isWon() || !winner.isWon(), msgError("wonError")),
				
				() -> assertEquals(3, loser.getValue1(), msgError("Value 1")),
				() -> assertEquals(1, loser.getValue2(), msgError("value 2")),
				() -> assertEquals(player, loser.getPlayer(), msgError("Player")),
				() -> assertEquals(null, loser.getId(), msgError("nullError")),
				() -> assertEquals(null, loser.getRegistration(), msgError("nullError"))
		);
		
	}
	
	@Test
	@DisplayName("Getters/Setters DiceJson")
	public void testGettersSettersDiceJson() {
		
		assertAll(
				() -> assertEquals(5, diceJson.getValue1(), msgError("Value 1")),
				() -> assertEquals(2, diceJson.getValue2(), msgError("value 2")),
				() -> assertEquals(1, diceJson.getId(), msgError("Id")),
				() -> assertEquals(registration, diceJson.getRegistration(), msgError("Registration date")),
				() -> assertTrue(diceJson.isStatus(), msgError("Status True"))
		);
		
	}
	
	@Test
	@DisplayName("Parser Dice Object to Json")
	public void testParseDiceToJson() {
		
		DiceJson toCompare = DiceJson.from(winner);
		DiceJson toCompare2 = winner.toJson();
		DiceJson toCompare3 = dice.toJson();
		DiceJson toCompare4 = DiceJson.from(dice);
		
		assertAll(
				
				() -> assertEquals(5, toCompare.getValue1(), msgError("Value 1")),
				() -> assertEquals(2, toCompare.getValue2(), msgError("value 2")),
				() -> assertEquals(1, toCompare.getId(), msgError("Id")),
				() -> assertEquals(registration, toCompare.getRegistration(), msgError("Registration date")),
				() -> assertTrue(toCompare.isStatus()),
				
				() -> assertEquals(5, toCompare2.getValue1(), msgError("Value 1")),
				() -> assertEquals(2, toCompare2.getValue2(), msgError("value 2")),
				() -> assertEquals(1, toCompare2.getId(), msgError("Id")),
				() -> assertEquals(registration, toCompare2.getRegistration(), msgError("Registration date")),
				() -> assertTrue(toCompare2.isStatus(), msgError("Status True")),
				
				() -> assertEquals(toCompare, toCompare2, msgError("toCompare and toCompare2")),
				
				() -> assertTrue(toCompare3.getValue1() >= 1 && toCompare3.getValue1() <= 6, msgError("rangeError")),
				() -> assertTrue(toCompare3.getValue2() >= 1 && toCompare3.getValue2() <= 6, msgError("rangeError")),
				() -> assertEquals(null, toCompare3.getId(), msgError("nullError")),
				() -> assertEquals(null, toCompare3.getRegistration(), msgError("nullError")),
				() -> assertTrue(toCompare3.isStatus() || !toCompare3.isStatus(), msgError("wonError")),
				
				() -> assertTrue(toCompare4.getValue1() >= 1 && toCompare4.getValue1() <= 6, msgError("rangeError")),
				() -> assertTrue(toCompare4.getValue2() >= 1 && toCompare4.getValue2() <= 6, msgError("rangeError")),
				() -> assertEquals(null, toCompare4.getId(), msgError("nullError")),
				() -> assertEquals(null, toCompare4.getRegistration(), msgError("nullError")),
				() -> assertTrue(toCompare4.isStatus() || !toCompare4.isStatus(), msgError("wonError")),
				
				() -> assertEquals(toCompare3, toCompare4, msgError("toCompare and toCompare2"))
		);
	}
	

	private String msgError(String input) {
		switch (input) {
		case "nullError":
			return "It should be null because this value is set automatically by the database in this method";

		case "rangeError":
			return "It should be greater than one and less than six";
			
		case "wonError":
			return "It should be false or true when calculating the sum of dices values";
			
		default:
			return String.format("%s should be equals because they were created from the same attribute", input);
		}
	}
	
}
