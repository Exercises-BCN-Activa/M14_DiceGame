package com.dice_game.crud.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dice_game.crud.dto.Dice;

public interface IDice extends JpaRepository<Dice, Integer>{
	
}
