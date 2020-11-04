package com.dice_game.crud.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dice_game.crud.dto.Dice;
import com.dice_game.crud.dto.Player;

public interface IDice extends MongoRepository<Dice, String>{
	
	List<Dice> findByPlayer(Player player);
	
}
