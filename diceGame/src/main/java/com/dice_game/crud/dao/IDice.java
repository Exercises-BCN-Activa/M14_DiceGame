package com.dice_game.crud.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dice_game.crud.dto.Dice;

public interface IDice extends MongoRepository<Dice, String>{
	
	public List<Dice> findByPlayer(String player_id);
	
}
