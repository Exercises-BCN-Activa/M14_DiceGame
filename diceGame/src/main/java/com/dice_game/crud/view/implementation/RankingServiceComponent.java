package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.exceptions.RankingServImplException.throwsUp;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dice_game.crud.model.dao.DiceDAO;
import com.dice_game.crud.model.dao.PlayerDAO;
import com.dice_game.crud.model.dto.DiceJson;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.exceptions.RankingServImplException;

@Component
class RankingServiceComponent {

	@Autowired
	private PlayerDAO PLAYERS;

	@Autowired
	private DiceDAO DICES;

	float getWinningPercentageOfAllGames() throws RankingServImplException {
		setListOfAllRoundsOfTheGame();
		ifAllRoundsListDiceIsEmptyThrowException();
		calcWinningPercentage();
		return getWinningPercentage();
	}

	private void setListOfAllRoundsOfTheGame() {
		allRoundsListDice = DICES.findAll().parallelStream()
				.map(dice -> dice.toJson()).collect(Collectors.toList());
	}

	private void calcWinningPercentage() {
		int totalRounds = allRoundsListDice.size();
		float wonRounds = allRoundsListDice.parallelStream()
				.filter(dice -> dice.isWon()).count();
		winningPercentage = wonRounds / totalRounds * 100;
	}

	private float getWinningPercentage() {
		return winningPercentage;
	}

	List<PlayerJson> rankedListOfPlayersWithStatus() throws RankingServImplException {
		setRankedListOfAllPlayersWithStatus();
		return getRankedListPlayers();
	}

	private void setRankedListOfAllPlayersWithStatus() throws RankingServImplException {
		rankedListPlayers = PLAYERS.findAll().parallelStream()
				.filter(player -> player.status() > 0)
				.sorted(Comparator.comparing(Player::status)
						.reversed()).map(player -> player.toJson())
				.collect(Collectors.toList());
		ifRankedListPlayersIsEmptyThrowException();
	}

	private List<PlayerJson> getRankedListPlayers() {
		return rankedListPlayers;
	}

	List<PlayerJson> firstPositionListOfPlayers() throws RankingServImplException {
		setRankedListOfAllPlayersWithStatus();
		setFirstPositionListOfPlayers();
		return getFirstPositionListPlayers();
	}

	private void setFirstPositionListOfPlayers() {
		String winningStatus = rankedListPlayers.get(0).getStatus();
		firstPositionListPlayers = rankedListPlayers.parallelStream()
				.filter(player -> player.getStatus().equals(winningStatus))
				.collect(Collectors.toList());
	}

	private List<PlayerJson> getFirstPositionListPlayers() {
		return firstPositionListPlayers;
	}

	List<PlayerJson> lastPositionListOfPlayers() throws RankingServImplException {
		setRankedListOfAllPlayersWithStatus();
		setLastPositionListOfPlayers();
		return getLastPositionListPlayers();
	}

	private void setLastPositionListOfPlayers() {
		int lastPosition = rankedListPlayers.size() - 1;
		String loserStatus = rankedListPlayers.get(lastPosition).getStatus();
		lastPositionListPlayers = rankedListPlayers.parallelStream()
				.filter(player -> player.getStatus().equals(loserStatus))
				.collect(Collectors.toList());
	}

	private void ifAllRoundsListDiceIsEmptyThrowException() throws RankingServImplException {
		if (allRoundsListDice.isEmpty())
			throwsUp("It seems that there is no Dice to be analyzed");
	}

	private void ifRankedListPlayersIsEmptyThrowException() throws RankingServImplException {
		if (rankedListPlayers.isEmpty())
			throwsUp("It seems that there is no Player to be analyzed");
	}

	private List<PlayerJson> getLastPositionListPlayers() {
		return lastPositionListPlayers;
	}

	private List<PlayerJson> rankedListPlayers;
	private List<PlayerJson> firstPositionListPlayers;
	private List<PlayerJson> lastPositionListPlayers;
	private List<DiceJson> allRoundsListDice;
	private float winningPercentage;

}
