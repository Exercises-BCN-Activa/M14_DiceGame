package com.dice_game.crud.view.implementation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dice_game.crud.model.dao.PlayerDAO;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.security.Role;
import com.dice_game.crud.utilities.Util;
import com.dice_game.crud.utilities.exceptions.PlayerServImplException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class PlayerServiceComponentTest {
	
	@MockBean
	private PlayerDAO DAO;
	
	@InjectMocks
	private PlayerServiceComponent service;
	
	private PlayerJson playerJson;
	private Player player;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Long id = 1l;

	@BeforeEach
	void setUp() throws Exception {
		firstName = "Fulano";
		lastName = "Ciclano";
		email = "john@somewhere.com";
		password = "senha2020";
		
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
		player.setStatus();
	}

	@Test
	@DisplayName("Should Throw Exception If Email Is Already Registered")
	void test_ifEmailIsAlreadyRegisteredThrowException() {
		Mockito.when(DAO.existsByEmail(email)).thenReturn(true, false);
		assertThrows(PlayerServImplException.class, 
				() -> service.ifEmailIsAlreadyRegisteredThrowException(playerJson),
				msgError("Throws - becuase mockito return true"));
		assertDoesNotThrow(() -> service.ifEmailIsAlreadyRegisteredThrowException(playerJson),
				msgError("Does Not Throws - becuase mockito return false"));
	}
	
	@Test
	@DisplayName("Should Return Json from Player Saved")
	void test_savePlayerByJsonReturnJson() {
		Mockito.when(DAO.save(playerJson.toPlayer())).thenReturn(player);
		PlayerJson toCompare = service.savePlayerByJsonReturnJson(playerJson);
		assertAll(
				() -> assertEquals(playerJson.getEmail(), toCompare.getEmail(), msgError("Equals 1")),
				() -> assertEquals(null, toCompare.getPassword(), msgError("Equals 2")),
				() -> assertEquals(playerJson.getFirstName(), toCompare.getFirstName(), msgError("Equals 3")),
				() -> assertEquals(playerJson.getLastName(), toCompare.getLastName(), msgError("Equals 4")),
				() -> assertEquals(player.getId(), toCompare.getId(), msgError("Equals 5")),
				() -> assertEquals(player.getRegistration(), toCompare.getRegistration(), msgError("Equals 6")),
				() -> assertEquals("never played", toCompare.getStatus(), msgError("Equals 7"))
				);
		
	}
	
	@Test
	@DisplayName("Find by Email Should Return Player Saved and Does Not Throw Nothing")
	void test1_findPlayerByEmail() {
		Mockito.when(DAO.findByEmail(email)).thenReturn(Optional.of(player));
		Player toCompare = service.findPlayerByEmail(email);
		assertEquals(player, toCompare, msgError("Equals 1"));
	}
	
	@Test
	@DisplayName("Find by Email Should Throw Exception Because Optional<Player> is Empty")
	void test2_findPlayerByEmail() {
		Mockito.when(DAO.findByEmail(email)).thenReturn(Optional.empty());
		assertThrows(PlayerServImplException.class, () -> service.findPlayerByEmail(email),
				msgError("Throws - becuase there's no one with that email"));
	}
	
	@Test
	@DisplayName("Find by Email Should Throw Exception Because Emails are Incorrects")
	void test3_findPlayerByEmail() {
		String invalidEmail = ".@somewhere.com";
		assertAll(
				() -> assertThrows(PlayerServImplException.class, () -> service.findPlayerByEmail(""),
						msgError("Throws 1 - becuase is empty")),
				() -> assertThrows(PlayerServImplException.class, () -> service.findPlayerByEmail(null),
						msgError("Throws 2 - becuase is null")),
				() -> assertThrows(PlayerServImplException.class, () -> service.findPlayerByEmail(invalidEmail),
						msgError("Throws 3 - becuase is invalid"))
				);
	}

	@Test
	@DisplayName("Find by ID Should Return Player Saved and Does Not Throw Nothing")
	void test1_findPlayerByID() {
		Mockito.when(DAO.findById(id)).thenReturn(Optional.of(player));
		Player toCompare = service.findPlayerByID(id);
		assertEquals(player, toCompare, msgError("Equals 1"));
	}
	
	@Test
	@DisplayName("Find by ID Should Throw Exception Because Optional<Player> is Empty")
	void test2_findPlayerByID() {
		Mockito.when(DAO.findById(id)).thenReturn(Optional.empty());
		assertThrows(PlayerServImplException.class, () -> service.findPlayerByID(id),
				msgError("Throws - becuase there's no one with that ID"));
	}

	@Test
	@DisplayName("Find by ID Should Throw Exception Because IDs are Incorrects")
	void test3_findPlayerByID() {
		assertAll(
				() -> assertThrows(PlayerServImplException.class, () -> service.findPlayerByID(0l),
						msgError("Throws 1 - becuase is empty")),
				() -> assertThrows(PlayerServImplException.class, () -> service.findPlayerByID(null),
						msgError("Throws 2 - becuase is null")),
				() -> assertThrows(PlayerServImplException.class, () -> service.findPlayerByID(-1l),
						msgError("Throws 3 - becuase is invalid"))
				);
	}
	
	@Test
	@DisplayName("Find by ID or Email Should Throw Exception Because all are empty")
	void test1_findPlayerByEmailOrId() {
		assertThrows(PlayerServImplException.class, () -> service.findPlayerByEmailOrId(new PlayerJson()),
				msgError("Throws - becuase ID and Email are invalid"));
	}
	
	@Test
	@DisplayName("Find by ID or Email* Should Return Player Saved and Does Not Throw Nothing")
	void test2_findPlayerByEmailOrId() {
		Mockito.when(DAO.findByEmail(email)).thenReturn(Optional.of(player));
		Player toCompare = service.findPlayerByEmailOrId(playerJson);
		assertEquals(player, toCompare, msgError("Equals 1"));
	}
	
	@Test
	@DisplayName("Find by ID* or Email Should Return Player Saved and Does Not Throw Nothing")
	void test3_findPlayerByEmailOrId() {
		playerJson.setId(id);
		playerJson.setEmail("");
		Mockito.when(DAO.findById(playerJson.getId())).thenReturn(Optional.of(player));
		Player toCompare = service.findPlayerByEmailOrId(playerJson);
		assertEquals(player, toCompare, msgError("Equals 1"));
	}
	
	@Test
	@DisplayName("List of All Player Should Return List of Json")
	void test1_findAllPlayers() {
		List<Player> listPlayer = new ArrayList<Player>();
		listPlayer.add(player);
		Mockito.when(DAO.findAll()).thenReturn(listPlayer);
		List<PlayerJson> listJson = service.findAllPlayers();
		PlayerJson toCompare = player.toJson();
		assertAll(
				() -> assertTrue(listJson.size()>0, msgError("True")),
				() -> assertEquals(toCompare, listJson.get(0), msgError("Equals"))
				);
	}
	
	@Test
	@DisplayName("List of All Player Should Return EMPTY List of Json")
	void test2_findAllPlayers() {
		Mockito.when(DAO.findAll()).thenReturn(new ArrayList<Player>());
		List<PlayerJson> listJson = service.findAllPlayers();
		assertTrue(listJson.size() == 0, msgError("Not Null"));
	}
	
	@Test
	@DisplayName("Update Method Should Throw Exception if all attributes are identical")
	void test1_updatePlayerByIdIfMeetRequirements() {
		playerJson.setId(id);
		Mockito.when(DAO.findById(playerJson.getId())).thenReturn(Optional.of(player));
		assertThrows(PlayerServImplException.class, 
				() -> service.updatePlayerByIdIfMeetRequirements(playerJson),
				msgError("Throws - because there is nothing to update"));
	}
	
	@Test
	@DisplayName("If the password is not correct throw exceptions")
	void test_ifPasswordDoesNotMatchThrowException() {
		Mockito.when(DAO.findByEmail(email)).thenReturn(Optional.of(player));
		
		assertDoesNotThrow(() -> service.ifPasswordDoesNotMatchThrowException(playerJson), 
				msgError("Does Not Throw - because there is the same"));
		
		playerJson.setPassword("ERRORtest");

		assertThrows(PlayerServImplException.class, 
				() -> service.ifPasswordDoesNotMatchThrowException(playerJson), 
				msgError("Does Throws - because there is NOT the same"));
		
	}
	
	@Test
	@DisplayName("Deletes a specific Player")
	void test_deleteEspecificPlayerIfWasUser() {
		Mockito.when(DAO.findByEmail(playerJson.getEmail())).thenReturn(Optional.of(player));
		Mockito.doAnswer((i) -> {assertEquals(player, i.getArgument(0));
								return null;}).when(DAO).delete(player);
		Mockito.when(DAO.existsByEmail(email)).thenReturn(true, false);
		assertThrows(PlayerServImplException.class, 
				() -> service.deleteEspecificPlayerIfWasUser(playerJson), 
				msgError("Throw - because mock return true"));
		assertDoesNotThrow(() -> service.deleteEspecificPlayerIfWasUser(playerJson), 
				msgError("Does Not Throws - because mock return false"));
	}
	
	@Test
	@DisplayName("If the list is empty should throw an exception")
	void teste1_deleteAllPlayersWhoHaveRoleUser() {
		Mockito.when(DAO.findAll()).thenReturn(new ArrayList<Player>());
		assertThrows(PlayerServImplException.class, 
				() -> service.deleteAllPlayersWhoHaveRoleUser(), 
				msgError("Does Throws - because the list is empty"));
	}
	
	@Test
	@DisplayName("If the list is NOT empty should throw an exception")
	void teste2_deleteAllPlayersWhoHaveRoleUser() {
		List<Player> list = new ArrayList<Player>();
		list.add(player);
		Player chief = new Player();
		chief.setType(Role.CHIEF);
		list.add(chief);
		Mockito.when(DAO.findAll()).thenReturn(list);
		Mockito.doAnswer((i) -> {assertEquals(player, i.getArgument(0));
								return null;}).when(DAO).delete(player);
		assertThrows(PlayerServImplException.class, 
				() -> service.deleteAllPlayersWhoHaveRoleUser(), 
				msgError("Does Throws - because the list is NOT empty"));
	}
	
	//the second call from the Mock (SAVE) is pointed to null
	@Disabled
	@Test
	@DisplayName("Update Method Should NOT Throw Exception AND Return")
	void test2_updatePlayerByIdIfMeetRequirements() {
		playerJson.setId(id);
		playerJson.setFirstName("Jonh");
		playerJson.setLastName("Somewhere");
		Mockito.when(DAO.findById(playerJson.getId())).thenReturn(Optional.of(player));
		Player updated = new Player();
		updated.setFirstName("Jonh");
		updated.setLastName("Somewhere");
		updated.setPassword(Util.encryptPassword(password));
		updated.setEmail(email);
		updated.setId(id);
		updated.setRegistration(new Date());
		updated.setType(Role.BASIC);
		updated.setRounds(new ArrayList<>());
		updated.setStatus();
		Mockito.when(DAO.save(updated)).thenReturn(updated);
		PlayerJson jsonUpdated = service.updatePlayerByIdIfMeetRequirements(playerJson);
		assertEquals(updated.toJson(), jsonUpdated);
	}
	
	private String msgError(String input) {
		return String.format("Error in Assert %s", input);
	}

}
