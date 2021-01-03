package com.dice_game.crud.view.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dice_game.crud.model.dao.DiceDAO;
import com.dice_game.crud.model.dto.Dice;
import com.dice_game.crud.model.dto.DiceJson;
import com.dice_game.crud.model.dto.Player;
import com.dice_game.crud.model.dto.PlayerJson;

@Component
final class DiceServiceComponent {
	
	@Autowired
	private PlayerServiceComponent playerService;
	
	@Autowired
	private DiceDAO DAO;


}
