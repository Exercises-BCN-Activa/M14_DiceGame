package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.Response.error;
import static com.dice_game.crud.utilities.Response.success;
import static com.dice_game.crud.utilities.Util.msgError;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.Response;
import com.dice_game.crud.view.service.RankingDetailService;

@Service
public final class RankingService implements RankingDetailService {
	
	@Autowired
	RankingServiceComponent service;

	@Override
	public Response percentageOfVictoriesForAllRounds() {
		
		Response response = error(msgError("Get Winning Percentage Of All Games"));
		
		try {
			
			float rateGame = service.getWinningPercentageOfAllGames();
			
			response = success("Winning Percentage successfully getted", rateGame);
			
		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response positionOfAllPlayersInRanking() {
		
		Response response = error(msgError("Get Ranked List of Players"));
		
		try {
			
			List<PlayerJson> ranking = service.rankedListOfPlayersWithStatus();
			
			response = success("Ranked List successfully getted", ranking);
			
		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response playerWinnerInRankingPosition() {
		
		Response response = error(msgError("Get Winners List of Players"));
		
		try {

			List<PlayerJson> winners = service.firstPositionListOfPlayers();
			
			response = success("Winners List successfully getted", winners);
			
		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response playerLoserInRankingPosition() {

		Response response = error(msgError("Get Losers List of Players"));
		
		try {

			List<PlayerJson> losers = service.lastPositionListOfPlayers();
			
			response = success("Losers List successfully getted", losers);
			
		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

}
