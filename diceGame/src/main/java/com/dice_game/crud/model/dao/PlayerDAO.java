package com.dice_game.crud.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dice_game.crud.model.dto.Player;

public interface PlayerDAO extends JpaRepository<Player, Long> {

	public Optional<Player> findByEmail(String email);
}
