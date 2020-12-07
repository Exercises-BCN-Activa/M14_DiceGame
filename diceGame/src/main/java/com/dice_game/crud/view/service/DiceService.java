package com.dice_game.crud.view.service;

import java.util.Map;

import com.dice_game.crud.model.dto.Dice;
import com.dice_game.crud.model.json.PlayerJson;

public interface DiceService {
	
	public Map<String, Object> createOne(PlayerJson playerJson);
	public Map<String, Object> readAllByPlayer(PlayerJson playerJson);
	public Map<String, Object> deleteAllByPlayer(PlayerJson playerJson);
	public Map<String, Object> deleteOne(Dice dice);
	public Map<String, Object> updateAll(String password);
	public Map<String, Object> deleteAll(String password);

}
