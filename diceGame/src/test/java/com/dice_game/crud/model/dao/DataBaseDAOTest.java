package com.dice_game.crud.model.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.dice_game.crud.model.dto.Dice;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.security.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
class DataBaseDAOTest {

	@Autowired
	private PlayerDAO playerDAO;
	
	@Autowired
	private DiceDAO diceDAO;

	private static Player player;

	private static String firstName;
	private static String lastName;
	private static String email;
	private static String password;
	
	private static Dice dice;

	@BeforeAll
	public static void setVariables() {
		firstName = "Fulano";
		lastName = "Ciclano";
		email = "fulano@cliclano.com";
		password = "senha2020";

		player = new Player();
		player.setEmail(email);
		player.setPassword(password);
		player.setFirstName(firstName);
		player.setLastName(lastName);
		player.setType(Role.FULL);
		player.setRounds(new ArrayList<Dice>());
	}

	@Test
	@Order(1)
	@Rollback(false)
	@DisplayName("Create a new player in DB")
	public void testCreatePlayer() {

		Player toCompare = playerDAO.save(player);

		assertAll(
				() -> assertEquals(player.getEmail(), toCompare.getEmail(), msgError("E-mail")),
				() -> assertEquals(player.getFirstName(), toCompare.getFirstName(), msgError("First name")),
				() -> assertEquals(player.getLastName(), toCompare.getLastName(), msgError("Last name")),
				() -> assertEquals(player.status(), toCompare.status(), msgError("Status")),
				() -> assertEquals(Role.FULL.getRole(), toCompare.getType(), msgError("Role Type")),
				() -> assertEquals(player.getPassword(), toCompare.getPassword(), msgError("Password")),
				() -> assertNotNull(toCompare.getId(), "Id can't be null, because is autogenerate in DB"),
				() -> assertNotNull(toCompare.getRegistration(),
						"Register Date can't be null, because is autogenerate in DB"));

		player.setId(toCompare.getId());
		player.setRegistration(toCompare.getRegistration());

		assertEquals(player, toCompare, msgError("Player and toCompare"));
	}

	@Test
	@Order(2)
	@DisplayName("Find Player by ID")
	public void testFindPlayerById() {
		Player toCompare = playerDAO.findById(player.getId()).get();
		
		assertAll(
				() -> assertEquals(player.getEmail(), toCompare.getEmail(), msgError("E-mail")),
				() -> assertEquals(player.getFirstName(), toCompare.getFirstName(), msgError("First name")),
				() -> assertEquals(player.getLastName(), toCompare.getLastName(), msgError("Last name")),
				() -> assertEquals(player.status(), toCompare.status(), msgError("Status")),
				() -> assertEquals(Role.FULL.getRole(), toCompare.getType(), msgError("Role Type")),
				() -> assertEquals(player.getPassword(), toCompare.getPassword(), msgError("Password")),
				() -> assertEquals(player.getId(), toCompare.getId(), msgError("Id")),
				() -> assertEquals(player.getRegistration(), toCompare.getRegistration(), msgError("Register"))
				);
	}

	@Test
	@Order(3)
	@DisplayName("Find Player by Email")
	public void testFindPlayerByEmail() {
		Player toCompare = playerDAO.findByEmail(email).get();

		assertAll(
				() -> assertEquals(player.getEmail(), toCompare.getEmail(), msgError("E-mail")),
				() -> assertEquals(player.getFirstName(), toCompare.getFirstName(), msgError("First name")),
				() -> assertEquals(player.getLastName(), toCompare.getLastName(), msgError("Last name")),
				() -> assertEquals(player.status(), toCompare.status(), msgError("Status")),
				() -> assertEquals(Role.FULL.getRole(), toCompare.getType(), msgError("Role Type")),
				() -> assertEquals(player.getPassword(), toCompare.getPassword(), msgError("Password")),
				() -> assertEquals(player.getId(), toCompare.getId(), msgError("Id")),
				() -> assertEquals(player.getRegistration(), toCompare.getRegistration(), msgError("Register"))
				);
	}
	
	@Test
	@Order(4)
	@DisplayName("Exists Player by Email")
	public void testExistsPlayerByEmail() {
		assertTrue(playerDAO.existsByEmail(email));
	}

	@Test
	@Order(5)
	@Rollback(false)
	@DisplayName("Update Player by Email")
	public void testUpdatePlayerByEmail() {
		Player toCompare = playerDAO.findByEmail(email).get();

		lastName = "Beltrano Ciclano";

		toCompare.setLastName(lastName);

		Player toCompareAfterUpdate = playerDAO.save(toCompare);

		assertAll(() -> assertNotNull(toCompare), () -> assertNotNull(toCompareAfterUpdate),
				() -> assertEquals(toCompare, toCompareAfterUpdate, msgError("toCompare and toCompareAfterUpdate")));
	}
	
	@Test
	@Order(6)
	@Rollback(false)
	@DisplayName("Create a new Dice in DB")
	public void testCreateDice() {
		dice = Dice.newRound(player);
		Dice toCompare = diceDAO.save(dice);
		
		assertAll(() -> assertNotNull(toCompare),
				() -> assertNotNull(toCompare.getId()),
				() -> assertNotNull(toCompare.getRegistration()),
				() -> assertEquals(dice.getPlayer(), toCompare.getPlayer(), msgError("Player")),
				() -> assertEquals(dice.getValue1(), toCompare.getValue1(), msgError("Value 1")),
				() -> assertEquals(dice.getValue2(), toCompare.getValue2(), msgError("Value 2")));
		
		dice.setId(toCompare.getId());
		dice.setRegistration(toCompare.getRegistration());
		
		assertEquals(dice, toCompare, msgError("Dice and toCompare"));
		
	}
	
	
	@Test
	@Order(7)
	@DisplayName("Find Dice by ID")
	public void testFindDiceById() {
		boolean existsInDataBase = diceDAO.existsById(dice.getId());
		
		assertTrue(existsInDataBase);
		
		Dice toCompare = diceDAO.findById(dice.getId()).get();
		
		assertAll(
				() -> assertEquals(dice.getValue1(), toCompare.getValue1(), msgError("Value1")),
				() -> assertEquals(dice.getValue2(), toCompare.getValue2(), msgError("Value2")),
				() -> assertEquals(dice.getPlayer().getId(), toCompare.getPlayer().getId(), msgError("Player ID")),
				() -> assertEquals(dice.getId(), toCompare.getId(), msgError("Dice and toCompare")),
				() -> assertNotNull(toCompare.getRegistration())
				);
	}
	
	
	@Test
	@Order(8)
	@DisplayName("Not Find Dice by ID")
	public void testNotFindDiceById() {
		boolean existsInDataBase = diceDAO.existsById(0l);
		
		assertFalse(existsInDataBase);
		
		boolean isPresent = diceDAO.findById(0l).isPresent();
		
		assertFalse(isPresent);
		
	}


	@Test
	@Order(9)
	@DisplayName("Find Dice by Player ID")
	public void testFindDiceByPlayerId() {
		List<Dice> listDice = diceDAO.findByPlayerId(player.getId());
		
		assertFalse(listDice.isEmpty());
		
		Dice toCompare = listDice.get(0);
		
		assertAll(
				() -> assertEquals(dice.getValue1(), toCompare.getValue1(), msgError("Value1")),
				() -> assertEquals(dice.getValue2(), toCompare.getValue2(), msgError("Value2")),
				() -> assertEquals(dice.getPlayer().getId(), toCompare.getPlayer().getId(), msgError("Player ID")),
				() -> assertEquals(dice.getId(), toCompare.getId(), msgError("Dice and toCompare")),
				() -> assertNotNull(toCompare.getRegistration())
				);
	}
	
	@Test
	@Order(10)
	@DisplayName("Not Find Dice by Player ID")
	public void testNotFindDiceByPlayerId() {
		List<Dice> listDice = diceDAO.findByPlayerId(4202469l);
		
		assertTrue(listDice.isEmpty());
		
	}
	
	@Test
	@Order(11)
	@DisplayName("Delete Dice by ID")
	public void testDeleteByDiceId() {
		List<Dice> listDice = diceDAO.findByPlayerId(player.getId());

		assertFalse(listDice.isEmpty());
		
		listDice.stream().forEach(x -> diceDAO.deleteById(x.getId()));
		
		List<Dice> toCompare = diceDAO.findByPlayerId(player.getId());
		
		assertTrue(toCompare.isEmpty());
	}
	
	@Test
	@Order(12)
	@DisplayName("Delete Dice by Object")
	public void testDeleteByDice() {
		List<Dice> listDice = diceDAO.findByPlayerId(player.getId());
		
		assertFalse(listDice.isEmpty());
		
		listDice.stream().forEach(x -> diceDAO.delete(x));
		
		List<Dice> toCompare = diceDAO.findByPlayerId(player.getId());
		
		assertTrue(toCompare.isEmpty());
	}
	
	@Test
	@Order(13)
	@Rollback(false)
	@DisplayName("Delete Player by ID")
	public void testDeletePlayerById() {
		boolean beforeDelete = playerDAO.findByEmail(email).isPresent();

		playerDAO.deleteById(player.getId());

		boolean afterDelete = playerDAO.findByEmail(email).isPresent();

		assertAll(() -> assertTrue(beforeDelete), () -> assertFalse(afterDelete));
	}
	

	@Test
	@Order(14)
	@DisplayName("Not Find Dice by Player ID After Delete Player")
	public void testNotFindDiceByPlayerIdAfterDeletePlayer() {
		List<Dice> listDice = diceDAO.findByPlayerId(player.getId());
		
		assertTrue(listDice.isEmpty());
		
	}

	@Test
	@Order(15)
	@DisplayName("Not Can Find Player by Email")
	public void testNoFindPlayerByEmail() {
		
		assertFalse(playerDAO.findByEmail(email).isPresent());
	}
	
	private String msgError(String input) {
		return String.format("%s should be equals because they were created from the same attribute", input);
	}

}
