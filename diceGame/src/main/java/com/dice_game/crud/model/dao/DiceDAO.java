package com.dice_game.crud.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dice_game.crud.model.dto.Dice;

public interface DiceDAO extends JpaRepository<Dice, Long> {

	public List<Dice> findByPlayerId(Long playerId);
}
