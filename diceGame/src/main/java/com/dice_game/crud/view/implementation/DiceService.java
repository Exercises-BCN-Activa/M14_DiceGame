package com.dice_game.crud.view.implementation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dice_game.crud.model.dao.DiceDAO;
import com.dice_game.crud.model.dto.Dice;
import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.view.service.DiceDetailService;

@Service
public final class DiceService implements DiceDetailService {
	
	@Autowired
	private DiceDAO dao;

	@Override
	public Map<String, Object> createOne(PlayerJson playerJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> readAllByPlayer(PlayerJson playerJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteAllByPlayer(PlayerJson playerJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteOne(Dice dice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteAll(String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
