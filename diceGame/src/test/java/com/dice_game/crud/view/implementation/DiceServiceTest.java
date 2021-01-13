package com.dice_game.crud.view.implementation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dice_game.crud.model.dto.Dice;
import com.dice_game.crud.model.dto.DiceJson;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.Response;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class DiceServiceTest {
	
	@Mock
	private DiceServiceComponent component;
	
	@InjectMocks
	private DiceService service;
	
	private final String CONTENT_ERROR = "This Response have no content!";
	private final String MESSAGE_ERROR = "Sorry, this Response has no message!";
	private final List<DiceJson> LIST_JSON = new ArrayList<DiceJson>();
	private final DiceJson NEW_ROUND = DiceJson.from(new Dice());
	private final PlayerJson PLAYER_JSON = PlayerJson.from(new Player());
	private final boolean IS_DELETED = true;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Error Response - Create Dice to a Players")
	void test1_createRound() {
		when(component.createNewRoundToPlayerBy(any(PlayerJson.class))).thenThrow(PlayerServImplException.class);
		Response toTesting = service.createRound(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Create Dice to a Players")
	void test2_createRound() {
		when(component.createNewRoundToPlayerBy(any(PlayerJson.class))).thenReturn(NEW_ROUND);
		Response toTesting = service.createRound(PLAYER_JSON);
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("Round successfully created"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(NEW_ROUND), msgError("True 3"))
				);
	}
	
	@Test
	@DisplayName("Error Response - Reed Dices of Players")
	void test1_readRoundsByPlayer() {
		when(component.reedAllRoundsOfPlayerBy(any(PlayerJson.class))).thenThrow(PlayerServImplException.class);
		Response toTesting = service.readRoundsByPlayer(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Reed Dices of Players")
	void test2_readRoundsByPlayer() {
		when(component.reedAllRoundsOfPlayerBy(any(PlayerJson.class))).thenReturn(LIST_JSON);
		Response toTesting = service.readRoundsByPlayer(PLAYER_JSON);
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("Round successfully collected"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(LIST_JSON), msgError("True 3"))
				);
	}

	@Test
	@DisplayName("Error Response - Delete Dices of Players")
	void test1_deleteRoundsByPlayer() {
		when(component.deleteAllRoundsOfPlayerBy(any(PlayerJson.class))).thenThrow(PlayerServImplException.class);
		Response toTesting = service.deleteRoundsByPlayer(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Delete Dices of Players")
	void test2_deleteRoundsByPlayer() {
		when(component.deleteAllRoundsOfPlayerBy(any(PlayerJson.class))).thenReturn(IS_DELETED);
		Response toTesting = service.deleteRoundsByPlayer(PLAYER_JSON);
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("Rounds successfully deleted"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(IS_DELETED), msgError("True 3"))
				);
	}

	@Test
	@DisplayName("Error Response - Delete All Dices From Game")
	void test1_deleteRoundsFromTheEntireGame() {
		when(component.ifIsAdminAndDeleteAllRoundsOfGame(any(PlayerJson.class))).thenThrow(PlayerServImplException.class);
		Response toTesting = service.deleteRoundsFromTheEntireGame(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Delete All Dices From Game")
	void test2_deleteRoundsFromTheEntireGame() {
		when(component.ifIsAdminAndDeleteAllRoundsOfGame(any(PlayerJson.class))).thenReturn(IS_DELETED);
		Response toTesting = service.deleteRoundsFromTheEntireGame(PLAYER_JSON);
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("GENERAL RESET: all rounds was erased"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(IS_DELETED), msgError("True 3"))
				);
	}
	
	private String msgError(String input) {
		return String.format("Error in Assert %s", input);
	}

}
