package com.dice_game.crud.view.implementation;

import static com.dice_game.crud.utilities.Response.error;
import static com.dice_game.crud.utilities.Response.success;
import static com.dice_game.crud.utilities.Util.msgError;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dice_game.crud.model.dto.DiceJson;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.Response;
import com.dice_game.crud.view.service.DiceDetailService;

@Service
public final class DiceService implements DiceDetailService {
	
	@Autowired
	private DiceServiceComponent service;

	@Override
	public Response createRound(PlayerJson playerJson) {
		
		Response response = error(msgError("Create a Round"));
		
		try {
			
			DiceJson newRound = service.createNewRoundToPlayerBy(playerJson);
			
			response = success("Round successfully created", newRound);
			
		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response readRoundsByPlayer(PlayerJson playerJson) {
		
		Response response = error(msgError("Read Rounds By Player"));
		
		try {
			
			List<DiceJson> diceJsonRounds = service.reedAllRoundsOfPlayerBy(playerJson);

			response = success("Round successfully collected", diceJsonRounds);
			
		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response deleteRoundsByPlayer(PlayerJson playerJson) {
		
		Response response = error(msgError("Delete Rounds By Player"));
		
		try {
			
			boolean isDeleted = service.deleteAllRoundsOfPlayerBy(playerJson);

			response = success("Rounds successfully deleted", isDeleted);
			
		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response deleteRoundsFromTheEntireGame(PlayerJson playerJson) {
		
		Response response = error(msgError("DELETE ALL: Reset the entire game"));
		
		try {
			
			boolean isAllDeleted = service.ifIsAdminAndDeleteAllRoundsOfGame(playerJson);

			response = success("GENERAL RESET: all rounds was erased", isAllDeleted);
			
		} catch (Exception e) {
			response = error(e.getMessage());
		}
		
		return response;
	}

}
