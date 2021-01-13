package com.dice_game.crud.view.implementation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.security.Role;
import com.dice_game.crud.utilities.Response;
import com.dice_game.crud.utilities.Util;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class PlayerServiceTest {

	@Mock
	private PlayerServiceComponent component;
	
	@InjectMocks
	private PlayerService service;

	private final String CONTENT_ERROR = "This Response have no content!";
	private final String MESSAGE_ERROR = "Sorry, this Response has no message!";
	private final Player PLAYER = createPlayer(1);
	private final PlayerJson PLAYER_JSON = PlayerJson.from(PLAYER);
	private final List<PlayerJson> LIST_JSON = new ArrayList<PlayerJson>();
	private Response toTesting;
	
	@BeforeAll
	static void setStaticMocks() {
		mockStatic(PlayerJsonToSaveValidation.class);
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		toTesting = null;
		
	}

	@Test
	@DisplayName("Error Response 1 - Create a Player")
	void test1_createOne() {
		doThrow(PlayerServImplException.class).when(component).ifEmailIsAlreadyRegisteredThrowException(any(PlayerJson.class));
		toTesting = service.createOne(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Error Response 2 - Create a Player")
	void test2_createOne() {
		doNothing().when(component).ifEmailIsAlreadyRegisteredThrowException(any(PlayerJson.class));
		doThrow(PlayerServImplException.class).when(component).savePlayerByJsonReturnJson(any(PlayerJson.class));
		toTesting = service.createOne(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Create a Player")
	void test3_createOne() {
		doNothing().when(component).ifEmailIsAlreadyRegisteredThrowException(any(PlayerJson.class));
		when(component.savePlayerByJsonReturnJson(any(PlayerJson.class))).thenReturn(PLAYER_JSON);
		toTesting = service.createOne(PLAYER_JSON);
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("Player successfully created"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(PLAYER_JSON), msgError("True 3"))
				);
	}
	
	@Test
	@DisplayName("Error Response 1 - Read All Players")
	void test1_readAll() {
		doThrow(PlayerServImplException.class).when(component).ifPasswordDoesNotMatchThrowException(PLAYER_JSON);
		toTesting = service.readAll(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}

	@Test
	@DisplayName("Error Response 2 - Read All Players")
	void test2_readAll() {
		doNothing().when(component).ifPasswordDoesNotMatchThrowException(PLAYER_JSON);
		doThrow(PlayerServImplException.class).when(component).findAllPlayers();
		toTesting = service.readAll(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Read All Players")
	void test3_readAll() {
		doNothing().when(component).ifPasswordDoesNotMatchThrowException(any(PlayerJson.class));
		when(component.findAllPlayers()).thenReturn(LIST_JSON);
		toTesting = service.readAll(PLAYER_JSON);
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("List of All Players in DataBase"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(LIST_JSON), msgError("True 3"))
				);
	}

	@Test
	@DisplayName("Error Response - Read a Player")
	void test1_readOne() {
		doThrow(PlayerServImplException.class).when(component).findPlayer(any(PlayerJson.class));
		toTesting = service.readOne(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Read a Player")
	void test2_readOne() {
		when(component.findPlayer(any(PlayerJson.class))).thenReturn(PLAYER_JSON);
		toTesting = service.readOne(PLAYER_JSON);
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("Player successfully finded"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(PLAYER_JSON), msgError("True 3"))
				);
	}

	@Test
	@DisplayName("Error Response - Update a Player")
	void test1_updateOne() {
		doThrow(PlayerServImplException.class).when(component).updatePlayerByIdIfMeetRequirements(any(PlayerJson.class));
		toTesting = service.updateOne(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Update a Player")
	void test2_updateOne() {
		when(component.updatePlayerByIdIfMeetRequirements(any(PlayerJson.class))).thenReturn(PLAYER_JSON);
		toTesting = service.updateOne(PLAYER_JSON);
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("Player updated successfully"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(PLAYER_JSON), msgError("True 3"))
				);
	}

	@Test
	@DisplayName("Error Response - Delete a Player")
	void test1_deleteOne() {
		doThrow(PlayerServImplException.class).when(component).deleteEspecificPlayerIfWasUser(any(PlayerJson.class));
		toTesting = service.deleteOne(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Delete a Player")
	void test2_deleteOne() {
		doNothing().when(component).deleteEspecificPlayerIfWasUser(any(PlayerJson.class));
		toTesting = service.deleteOne(PLAYER_JSON);
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("The Player was correctly deleted"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 3"))
				);
	}

	@Test
	@DisplayName("Error Response 1 - Delete All Players")
	void test1_deleteAll() {
		doThrow(PlayerServImplException.class).when(component).ifPasswordDoesNotMatchThrowException(any(PlayerJson.class));
		toTesting = service.deleteAll(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}

	@Test
	@DisplayName("Error Response 2 - Delete All Players")
	void test2_deleteAll() {
		doNothing().when(component).ifPasswordDoesNotMatchThrowException(any(PlayerJson.class));
		doThrow(PlayerServImplException.class).when(component).deleteAllPlayersWhoHaveRoleUser();
		toTesting = service.deleteAll(PLAYER_JSON);
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertEquals(MESSAGE_ERROR, toTesting.getMessage(), msgError("Equals 1")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 1"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Delete All Players")
	void test3_deleteAll() {
		doNothing().when(component).ifPasswordDoesNotMatchThrowException(any(PlayerJson.class));
		doNothing().when(component).deleteAllPlayersWhoHaveRoleUser();
		toTesting = service.deleteAll(PLAYER_JSON);
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("All users have been deleted"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 3"))
				);
	}

	private static Player createPlayer(int id) {
		Player player = new Player();
		player.setFirstName("first" + id);
		player.setLastName("last" + id);
		player.setPassword(Util.encryptPassword("senha" + id));
		player.setEmail(id + "@email.com");
		player.setId(Long.valueOf(id));
		player.setRegistration(new Date());
		player.setType(Role.BASIC);
		player.setRounds(new ArrayList<Dice>());
		return player;
	}
	
	private String msgError(String input) {
		return String.format("Error in Assert %s", input);
	}

}
