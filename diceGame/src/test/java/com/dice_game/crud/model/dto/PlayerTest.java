package com.dice_game.crud.model.dto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dice_game.crud.security.Role;

class PlayerTest {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Date registration;
	private String password;
	private Player player;
	private PlayerJson playerJson;

	@BeforeEach
	public void setUp() {
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
		player.setRegistration(registration);
		player.setType(Role.BASIC);
		player.setPassword(password);

		playerJson = new PlayerJson();
		playerJson.setId(id);
		playerJson.setEmail(email);
		playerJson.setRegistration(registration);
		playerJson.setFullName(firstName, lastName);
		playerJson.setStatus(0);
		playerJson.setPassword(password);
	}

	@Test
	@DisplayName("Calculation of status by Dice list")
	public void testStatusCalculating() {

		float beforePutDices = player.status();

		Dice winner = Dice.newRound(player);
		winner.setValue1(5);
		winner.setValue2(2);

		Dice loser = Dice.newRound(player);
		loser.setValue1(2);
		loser.setValue2(2);

		List<Dice> dices = new ArrayList<>();
		dices.add(loser);
		dices.add(loser);
		dices.add(loser);
		dices.add(loser);
		dices.add(winner);

		player.setRounds(dices);

		assertAll(() -> assertEquals(0, beforePutDices, "it should be zero because there was no game yet"),
				() -> assertEquals(20, player.status(), "should be 20% as there is only 1 winning out of 5 games"));
	}

	@Test
	@DisplayName("Parse Player Object to Json")
	public void testPlayerToJson() {

		PlayerJson jsonToCompare = player.toJson();

		PlayerJson jsonToCompare2 = PlayerJson.from(player);

		Player playerToCompare = jsonToCompare.toPlayer();
		playerToCompare.setId(id);
		playerToCompare.setPassword(password);
		playerToCompare.setRegistration(registration);

		Player playerToCompare2 = jsonToCompare2.toPlayer();
		playerToCompare2.setId(id);
		playerToCompare2.setPassword(password);
		playerToCompare2.setRegistration(registration);

		assertAll(() -> assertEquals(player.getId(), jsonToCompare.getId(), msgError("Id")),
				() -> assertEquals(player.getEmail(), jsonToCompare.getEmail(), msgError("E-mail")),
				() -> assertEquals(player.getRegistration(), jsonToCompare.getRegistration(),
						msgError("Registration Date")),
				() -> assertEquals(player.getFirstName(), jsonToCompare.getFirstName(), msgError("First name")),
				() -> assertEquals(player.getLastName(), jsonToCompare.getLastName(), msgError("Last name")),
				() -> assertEquals(String.format("%s, %s", lastName, firstName), jsonToCompare.getFullName(),
						msgError("Full name")),
				() -> assertEquals("never played", jsonToCompare.getStatus(), msgError("Status")),
				() -> assertEquals(null, jsonToCompare.getPassword(), msgError("Password")),
				() -> assertEquals(playerJson, jsonToCompare, msgError("jsonToCompare")),

				() -> assertEquals(player.getId(), jsonToCompare2.getId(), msgError("Id")),
				() -> assertEquals(player.getEmail(), jsonToCompare2.getEmail(), msgError("E-mail")),
				() -> assertEquals(player.getRegistration(), jsonToCompare2.getRegistration(),
						msgError("Registration Date")),
				() -> assertEquals(player.getFirstName(), jsonToCompare2.getFirstName(), msgError("First name")),
				() -> assertEquals(player.getLastName(), jsonToCompare2.getLastName(), msgError("Last name")),
				() -> assertEquals(String.format("%s, %s", lastName, firstName), jsonToCompare2.getFullName(),
						msgError("Full name")),
				() -> assertEquals("never played", jsonToCompare2.getStatus(), msgError("Status")),
				() -> assertEquals(null, jsonToCompare2.getPassword(), msgError("Password")),
				() -> assertEquals(playerJson, jsonToCompare2, msgError("jsonToCompare2")),

				() -> assertEquals(jsonToCompare, jsonToCompare2, msgError("jsonToCompare and jsonToCompare2")),

				() -> assertEquals(player, playerToCompare, msgError("playerToCompare")),
				() -> assertEquals(player, playerToCompare2, msgError("playerToCompare2")),
				() -> assertEquals(playerToCompare, playerToCompare2,
						msgError("playerToCompare and playerToCompare2")));

	}

	@Test
	@DisplayName("Parse Json Object to Player")
	public void testJsonToPlayer() {

		Player playerToCompare = playerJson.toPlayer();
		playerToCompare.setId(id);
		playerToCompare.setPassword(password);
		playerToCompare.setRegistration(registration);

		PlayerJson jsonToCompare = playerToCompare.toJson();

		assertAll(() -> assertEquals(playerToCompare.getEmail(), playerJson.getEmail(), msgError("E-mail")),
				() -> assertEquals(playerToCompare.getFirstName(), playerJson.getFirstName(), msgError("First name")),
				() -> assertEquals(playerToCompare.getLastName(), playerJson.getLastName(), msgError("Last name")),
				() -> assertEquals(playerToCompare.getPassword(), playerJson.getPassword(), msgError("Password")),
				() -> assertEquals(playerToCompare.getType(), Role.BASIC.getRole(), msgError("Role Type")),
				() -> assertEquals(playerToCompare, player, msgError("playerToCompare")),
				() -> assertEquals(jsonToCompare, playerJson, msgError("jsonToCompare")));
	}

	@Test
	@DisplayName("Getters/Setters PlayerJson")
	public void testGettersSettersPlayerJson() {

		assertAll(() -> assertEquals(id, playerJson.getId(), msgError("Id")),
				() -> assertEquals(email, playerJson.getEmail(), msgError("E-mail")),
				() -> assertEquals(registration, playerJson.getRegistration(), msgError("Registration Date")),
				() -> assertEquals(firstName, playerJson.getFirstName(), msgError("First name")),
				() -> assertEquals(lastName, playerJson.getLastName(), msgError("Last name")),
				() -> assertEquals(String.format("%s, %s", lastName, firstName), playerJson.getFullName(),
						msgError("Full name")),
				() -> assertEquals("never played", playerJson.getStatus(), msgError("Status")),
				() -> assertEquals(password, playerJson.getPassword(), msgError("Password")));
	}

	@Test
	@DisplayName("Getters/Setters Player")
	public void testGettersSettersPlayer() {

		assertAll(() -> assertEquals(id, player.getId(), msgError("Id")),
				() -> assertEquals(email, player.getEmail(), msgError("E-mail")),
				() -> assertEquals(registration, player.getRegistration(), msgError("Registration Date")),
				() -> assertEquals(firstName, player.getFirstName(), msgError("First name")),
				() -> assertEquals(lastName, player.getLastName(), msgError("Last name")),
				() -> assertEquals(0, player.status(), msgError("Status")),
				() -> assertEquals(Role.BASIC.getRole(), player.getType(), msgError("Role Type")),
				() -> assertEquals(password, player.getPassword(), msgError("Password")));
	}

	private String msgError(String input) {
		return String.format("%s should be equals because they were created from the same attribute", input);
	}

}
