package com.dice_game.crud.view.implementation;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.Response;
import com.dice_game.crud.utilities.exceptions.RankingServImplException;

class RankingServiceTest {

	@Mock
	RankingServiceComponent component;
	
	@InjectMocks
	RankingService service;
	
	private final List<PlayerJson> LIST_JSON = new ArrayList<PlayerJson>();
	private final String CONTENT_ERROR = "Error Response have no content!";
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Error Response - Percentage Of Victories")
	void test1_percentageOfVictoriesForAllRounds() {
		when(component.getWinningPercentageOfAllGames()).thenThrow(RankingServImplException.class);
		Response toTesting = service.percentageOfVictoriesForAllRounds();
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertTrue(toTesting.getMessage().startsWith("Something went wrong trying to "), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().contains("Get Winning Percentage Of All Games"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 3"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Percentage Of Victories")
	void test2_percentageOfVictoriesForAllRounds() {
		float test = 420.69f;
		Mockito.when(component.getWinningPercentageOfAllGames()).thenReturn(test);
		Response toTesting = service.percentageOfVictoriesForAllRounds();
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("Winning Percentage successfully getted"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(test), msgError("True 3"))
				);
	}

	@Test
	@DisplayName("Error Response - Players Ranking")
	void test1_positionOfAllPlayersInRanking() {
		when(component.rankedListOfPlayersWithStatus()).thenThrow(RankingServImplException.class);
		Response toTesting = service.positionOfAllPlayersInRanking();
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertTrue(toTesting.getMessage().startsWith("Something went wrong trying to "), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().contains("Get Ranked List of Players"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 3"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Players Ranking")
	void test2_positionOfAllPlayersInRanking() {
		when(component.rankedListOfPlayersWithStatus()).thenReturn(LIST_JSON);
		Response toTesting = service.positionOfAllPlayersInRanking();
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("Ranked List successfully getted"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(LIST_JSON), msgError("True 3"))
				);
	}

	@Test
	@DisplayName("Error Response - Players Winners")
	void test1_playerWinnerInRankingPosition() {
		when(component.firstPositionListOfPlayers()).thenThrow(RankingServImplException.class);
		Response toTesting = service.playerWinnerInRankingPosition();
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertTrue(toTesting.getMessage().startsWith("Something went wrong trying to "), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().contains("Get Winners List of Players"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 3"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Players Winners")
	void test2_playerWinnerInRankingPosition() {
		when(component.firstPositionListOfPlayers()).thenReturn(LIST_JSON);
		Response toTesting = service.playerWinnerInRankingPosition();
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("Winners List successfully getted"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(LIST_JSON), msgError("True 3"))
				);
	}

	@Test
	@DisplayName("Error Response - Players Losers")
	void test1_playerLoserInRankingPosition() {
		when(component.lastPositionListOfPlayers()).thenThrow(RankingServImplException.class);
		Response toTesting = service.playerLoserInRankingPosition();
		assertAll(
				() -> assertFalse(toTesting.isSuccess(), msgError("False 1")),
				() -> assertTrue(toTesting.getMessage().startsWith("Something went wrong trying to "), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().contains("Get Losers List of Players"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(CONTENT_ERROR), msgError("True 3"))
				);
	}
	
	@Test
	@DisplayName("Success Response - Players Losers")
	void test2_playerLoserInRankingPosition() {
		when(component.lastPositionListOfPlayers()).thenReturn(LIST_JSON);
		Response toTesting = service.playerLoserInRankingPosition();
		assertAll(
				() -> assertTrue(toTesting.isSuccess(), msgError("True 1")),
				() -> assertTrue(toTesting.getMessage().equals("Losers List successfully getted"), msgError("True 2")),
				() -> assertTrue(toTesting.getContent().equals(LIST_JSON), msgError("True 3"))
				);
	}

	private String msgError(String input) {
		return String.format("Error in Assert %s", input);
	}

}
