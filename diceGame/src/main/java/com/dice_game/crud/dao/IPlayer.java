package com.dice_game.crud.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dice_game.crud.dto.Player;

public interface IPlayer extends JpaRepository<Player, Integer>{
	
}
