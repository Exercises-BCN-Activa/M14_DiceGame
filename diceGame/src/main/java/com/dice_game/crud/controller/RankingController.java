package com.dice_game.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dice_game.crud.utilities.Response;
import com.dice_game.crud.view.implementation.RankingService;

@RestController
@PreAuthorize("authenticated")
@RequestMapping("/api/ranking")
public class RankingController {
	
	@Autowired
	private RankingService service;
	
	@GetMapping("/successrate")
	@ResponseStatus(HttpStatus.OK)
	public Response getSuccessRate() {
		return service.percentageOfVictoriesForAllRounds();
	}
	
	@GetMapping("/players")
	@ResponseStatus(HttpStatus.OK)
	public Response getRankedListOfAllPlayers() {
		return service.positionOfAllPlayersInRanking();
	}
	
	@GetMapping("/winners")
	@ResponseStatus(HttpStatus.OK)
	public Response getWinnersList() {
		return service.playerWinnerInRankingPosition();
	}
	
	@GetMapping("/losers")
	@ResponseStatus(HttpStatus.OK)
	public Response getLosersList() {
		return service.playerLoserInRankingPosition();
	}

}
