package com.dice_game.crud.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.dice_game.crud.dto.Player;

public interface IPlayer extends MongoRepository<Player, String>{
	
}
