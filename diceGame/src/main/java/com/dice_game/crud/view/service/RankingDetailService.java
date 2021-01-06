package com.dice_game.crud.view.service;

import com.dice_game.crud.utilities.Response;

public interface RankingDetailService {
	
	public Response percentageOfVictoriesForAllRounds();
	public Response positionOfAllPlayersInRanking();
	public Response playerWinnerInRankingPosition();
	public Response playerLoserInRankingPosition();

}
