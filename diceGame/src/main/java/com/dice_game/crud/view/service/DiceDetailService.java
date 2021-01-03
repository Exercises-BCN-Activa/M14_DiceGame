package com.dice_game.crud.view.service;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.Response;

public interface DiceDetailService {
	
	public Response createRound(PlayerJson playerJson);
	public Response readRoundsByPlayer(PlayerJson playerJson);
	public Response deleteRoundsByPlayer(PlayerJson playerJson);
	public Response deleteRoundsFromTheEntireGame(PlayerJson playerJson);

}
