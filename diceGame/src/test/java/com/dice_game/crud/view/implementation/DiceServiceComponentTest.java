package com.dice_game.crud.view.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dice_game.crud.model.dao.DiceDAO;
import com.dice_game.crud.model.dao.PlayerDAO;
import com.dice_game.crud.model.dto.Dice;
import com.dice_game.crud.model.dto.DiceJson;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.security.Role;
import com.dice_game.crud.utilities.Util;
import com.dice_game.crud.utilities.exceptions.DiceServImplException;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class DiceServiceComponentTest {
	
	@MockBean
	private PlayerDAO PLAYER;
	
	@MockBean
	private DiceDAO DICE;
	
	@Mock
	private PlayerServiceComponent pService;

	@InjectMocks
	private DiceServiceComponent dService;
	
	private DiceJson diceJson;
	private Dice dice;
	private Dice diceWinner;
	private Dice diceLoser;
	private PlayerJson playerJson;
	private Player player;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Long id;
	private List<Dice> listDice;
	private List<DiceJson> listJson;

	@BeforeEach
	void setUp() throws Exception {
		firstName = "Fulano";
		lastName = "Ciclano";
		email = "john@somewhere.com";
		password = "senha2020";
		id = 1l;
		
		playerJson = new PlayerJson();
		playerJson.setFirstName(firstName);
		playerJson.setLastName(lastName);
		playerJson.setPassword(password);
		playerJson.setEmail(email);
		
		player = new Player();
		player.setFirstName(firstName);
		player.setLastName(lastName);
		player.setPassword(Util.encryptPassword(password));
		player.setEmail(email);
		player.setId(id);
		player.setRegistration(new Date());
		player.setType(Role.BASIC);
		player.setRounds(new ArrayList<>());
		
		dice = Dice.newRound(player);
		dice.setId(id);
		dice.setRegistration(new Date());
		
		diceJson = dice.toJson();

		diceWinner = Dice.newRound(player);
		diceWinner.setId(2l);
		diceWinner.setRegistration(new Date());
		diceWinner.setValue1(2);
		diceWinner.setValue2(5);
		
		diceLoser = Dice.newRound(player);
		diceLoser.setId(3l);
		diceLoser.setRegistration(new Date());
		diceLoser.setValue1(2);
		diceLoser.setValue2(3);
		
		listDice = new ArrayList<Dice>();
		listDice.add(dice);
		listDice.add(diceWinner);
		listDice.add(diceLoser);

		listJson = new ArrayList<DiceJson>();
		listJson.add(dice.toJson());
		listJson.add(diceWinner.toJson());
		listJson.add(diceLoser.toJson());
		
		Mockito.lenient().when(pService.findPlayerByEmailOrId(playerJson)).thenReturn(player);
		Mockito.lenient().when(PLAYER.findByEmail(any(String.class))).thenReturn(Optional.of(player));
		
	}

	@Test
	@DisplayName("Should Return DiceJson of Dice Saved")
	void test_createNewRoundToPlayerBy() {
		Mockito.when(DICE.save(any(Dice.class))).thenReturn(dice);
		DiceJson toCompare = dService.createNewRoundToPlayerBy(playerJson);
		assertEquals(diceJson, toCompare, msgError("Equals"));
	}
	
	@Test
	@DisplayName("Should Return List DiceJson of Dices Saved")
	void test1_reedAllRoundsOfPlayerBy() {
		Mockito.when(DICE.findByPlayerId(any(Long.class))).thenReturn(listDice);
		List<DiceJson> toCompare = dService.reedAllRoundsOfPlayerBy(playerJson);
		assertEquals(listJson, toCompare, msgError("Equals"));
	}
	
	@Test
	@DisplayName("Should Throws Exception If Player Has No Rounds to Show")
	void test2_reedAllRoundsOfPlayerBy() {
		Mockito.when(DICE.findByPlayerId(any(Long.class))).thenReturn(new ArrayList<Dice>());
		assertThrows(DiceServImplException.class, () -> dService.reedAllRoundsOfPlayerBy(playerJson), 
				msgError("Throws"));
	}
	
	@Test
	@DisplayName("Should Throws Exception If Player Has No Rounds to Delete")
	void test1_deleteAllRoundsOfPlayerBy() {
		Mockito.when(DICE.findByPlayerId(any(Long.class))).thenReturn(new ArrayList<Dice>());
		assertThrows(DiceServImplException.class, () -> dService.deleteAllRoundsOfPlayerBy(playerJson), 
				msgError("Throws"));
	}
	
	@SuppressWarnings("unchecked") //by the lambdas on the line 147
	@Test
	@DisplayName("Should Return True If Player Has Rounds And Do Delete")
	void test2_deleteAllRoundsOfPlayerBy() {
		Mockito.when(DICE.findByPlayerId(any(Long.class))).thenReturn(listDice);
		Mockito.doAnswer(i -> {
			((List<Dice>) i.getArgument(0)).clear();
			return null;
		}).when(DICE).deleteAll(listDice);
		boolean shouldBeTrue = dService.deleteAllRoundsOfPlayerBy(playerJson);
		assertTrue(shouldBeTrue, msgError("True - Because List has Dices"));
	}
	
	@Test
	@DisplayName("Should Throws Exception Because List still have Dices")
	void test3_deleteAllRoundsOfPlayerBy() {
		Mockito.when(DICE.findByPlayerId(any(Long.class))).thenReturn(listDice);
		Mockito.doNothing().when(DICE).deleteAll(listDice);
		Mockito.when(DICE.existsById(any(Long.class))).thenReturn(true);
		assertThrows(DiceServImplException.class, () -> dService.deleteAllRoundsOfPlayerBy(playerJson), 
				msgError("Throws"));
	}
	
	@Test
	@DisplayName("Should Throws Exception Because Password are incorrect")
	void test4_deleteAllRoundsOfPlayerBy() {
		playerJson.setPassword("error");
		Mockito.when(pService.findPlayerByEmailOrId(playerJson)).thenReturn(player);
		assertThrows(PlayerServImplException.class, () -> dService.deleteAllRoundsOfPlayerBy(playerJson), 
				msgError("Throws"));
	}

	@Test
	@DisplayName("Should Throws Exception Because Player Don't have authorization")
	void test1_ifIsAdminAndDeleteAllRoundsOfGame() {
		Mockito.doNothing().when(DICE).deleteAll();
		Mockito.when(DICE.existsById(any(Long.class))).thenReturn(true);
		assertThrows(DiceServImplException.class, () -> dService.ifIsAdminAndDeleteAllRoundsOfGame(playerJson), 
				msgError("Throws"));
	}
	
	@Test
	@DisplayName("Should Return True Because Player have authorization")
	void test2_ifIsAdminAndDeleteAllRoundsOfGame() {
		player.setType(Role.FULL);
		Mockito.when(pService.findPlayerByEmailOrId(playerJson)).thenReturn(player);
		Mockito.doNothing().when(DICE).deleteAll();
		Mockito.when(DICE.findAll()).thenReturn(new ArrayList<Dice>());
		boolean isAllErased = dService.ifIsAdminAndDeleteAllRoundsOfGame(playerJson);
		assertTrue(isAllErased, msgError("Throws"));
	}

	private String msgError(String input) {
		return String.format("Error in Assert %s", input);
	}
}
