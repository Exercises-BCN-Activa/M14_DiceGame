package com.dice_game.crud.view.service;

import java.util.Map;

import com.dice_game.crud.model.dto.PlayerJson;

public interface PlayerService {
	
	public Map<String, Object> createOne(PlayerJson playerJson);
	public Map<String, Object> readAll(String password);
	public Map<String, Object> readOne(PlayerJson playerJson);
	public Map<String, Object> updateOne(PlayerJson playerJson);
	public Map<String, Object> updateAll(String password);
	public Map<String, Object> deleteOne(PlayerJson playerJson);
	public Map<String, Object> deleteAll(String password);

}
