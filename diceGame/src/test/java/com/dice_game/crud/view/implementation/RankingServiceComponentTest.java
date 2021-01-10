package com.dice_game.crud.view.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dice_game.crud.model.dao.DiceDAO;
import com.dice_game.crud.model.dao.PlayerDAO;
import com.dice_game.crud.model.dto.Dice;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.security.Role;
import com.dice_game.crud.utilities.Util;
import com.dice_game.crud.utilities.exceptions.RankingServImplException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class RankingServiceComponentTest {
	
	@MockBean
	private PlayerDAO PLAYER;
	
	@MockBean
	private DiceDAO DICE;
	
	@InjectMocks
	private RankingServiceComponent service;
	
	private static List<Player> listPlayer;
	private static List<PlayerJson> listJson;
	private static List<Dice> listDice;

	@BeforeAll
	static void setUp() throws Exception {
		listPlayer = new ArrayList<Player>();
		listJson = new ArrayList<PlayerJson>();
		listDice = new ArrayList<Dice>();
		Player player;
		int x = 4;
		int y = 8;
		
		for (int i = 1; i <= 6; i++) {
			player = createPlayer(i);
		
			if (i<=5) {
			
				for (int j = x; j <= y; j++) {
					Dice dice = createDice(player, j);
					player.getRounds().add(dice);
				}
				
				x--;
				y--;
			}
			
			listPlayer.add(player);
			
			if (i<=4) {
				listJson.add(player.toJson());
			}
		}
		
		listPlayer.stream().forEach(p -> p.getRounds().stream().forEach(d -> listDice.add(d)));
	}
	
	@Test
	@DisplayName("Percentage Of Victories")
	void test1_getWinningPercentageOfAllGames() {
		Mockito.when(DICE.findAll()).thenReturn(listDice);
		float winningPercentage = service.getWinningPercentageOfAllGames();
		assertEquals(40.0, winningPercentage, 
				"Because there are 25 rounds and only 10 of them are winners");
	}
	
	@Test
	@DisplayName("Error in Percentage Of Victories")
	void test2_getWinningPercentageOfAllGames() {
		Mockito.when(DICE.findAll()).thenReturn(new ArrayList<Dice>());
		assertThrows(RankingServImplException.class, () -> service.getWinningPercentageOfAllGames(),
				"Because the Dice list is empty should throw exception!");
	}

	@Test
	@DisplayName("Ranked List")
	void test1_rankedListOfPlayersWithStatus() {
		Mockito.when(PLAYER.findAll()).thenReturn(listPlayer);
		List<PlayerJson> toCompare = service.rankedListOfPlayersWithStatus();
		assertEquals(listJson, toCompare, 
				"Because there are 6 players and only 4 of them have a status above 0");
	}
	
	@Test
	@DisplayName("Error in Ranked List")
	void test2_rankedListOfPlayersWithStatus() {
		Mockito.when(PLAYER.findAll()).thenReturn(new ArrayList<Player>());
		assertThrows(RankingServImplException.class, () -> service.rankedListOfPlayersWithStatus(),
				"Because the Player list is empty should throw exception!");
	}

	@Test
	@DisplayName("First Position Player With One Player")
	void test1_firstPositionListOfPlayers() {
		Mockito.when(PLAYER.findAll()).thenReturn(listPlayer);
		List<PlayerJson> winners = service.firstPositionListOfPlayers();
		List<PlayerJson> toCompare = new ArrayList<PlayerJson>(); 
		toCompare.add(listJson.get(0));
		assertEquals(toCompare, winners, 
				"Because only one player has the first position");
	}
	
	@Test
	@DisplayName("First Position Player With More than One Player")
	void test2_firstPositionListOfPlayers() {
		Player player = createPlayer(7);
		for (int i = 4; i <= 8; i++) {
			player.getRounds().add(createDice(player, i));
		}
		listPlayer.add(player);
		Mockito.when(PLAYER.findAll()).thenReturn(listPlayer);
		List<PlayerJson> winners = service.firstPositionListOfPlayers();
		List<PlayerJson> toCompare = new ArrayList<PlayerJson>(); 
		toCompare.add(listJson.get(0));
		toCompare.add(player.toJson());
		listPlayer.remove(player);
		assertEquals(toCompare, winners, 
				"Because more than one player has the first position");
	}
	
	@Test
	@DisplayName("Error in First Position Player With NO Player")
	void test3_firstPositionListOfPlayers() {
		Mockito.when(PLAYER.findAll()).thenReturn(new ArrayList<Player>());
		assertThrows(RankingServImplException.class, () -> service.firstPositionListOfPlayers(),
				"Because the Player list is empty should throw exception!");
	}

	@Test
	@DisplayName("Last Position Player With One Player")
	void test1_lastPositionListOfPlayers() {
		Mockito.when(PLAYER.findAll()).thenReturn(listPlayer);
		List<PlayerJson> losers = service.lastPositionListOfPlayers();
		List<PlayerJson> toCompare = new ArrayList<PlayerJson>(); 
		toCompare.add(listJson.get(3));
		assertEquals(toCompare, losers, 
				"Because only one player has the last position");
	}
	
	@Test
	@DisplayName("Last Position Player With More than One Player")
	void test2_lastPositionListOfPlayers() {
		Player player = createPlayer(7);
		for (int i = 1; i <= 5; i++) {
			player.getRounds().add(createDice(player, i));
		}
		listPlayer.add(player);
		Mockito.when(PLAYER.findAll()).thenReturn(listPlayer);
		List<PlayerJson> losers = service.lastPositionListOfPlayers();
		List<PlayerJson> toCompare = new ArrayList<PlayerJson>(); 
		toCompare.add(listJson.get(3));
		toCompare.add(player.toJson());
		listPlayer.remove(player);
		assertEquals(toCompare, losers, 
				"Because more than one player has the last position");
	}
	
	@Test
	@DisplayName("Error in last Position Player With NO Player")
	void test3_lastPositionListOfPlayers() {
		Mockito.when(PLAYER.findAll()).thenReturn(new ArrayList<Player>());
		assertThrows(RankingServImplException.class, () -> service.lastPositionListOfPlayers(),
				"Because the Player list is empty should throw exception!");
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
	
	private static Dice createDice(Player player, int id) {
		Dice dice = Dice.newRound(player);
		String diceId = player.getId().toString() + id;
		dice.setId(Long.valueOf(diceId));
		dice.setRegistration(new Date());
		dice.setValue1(2);
		dice.setValue2((id < 5) ? 3 : 5);
		return dice;
	}

}
