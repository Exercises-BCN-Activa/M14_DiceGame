package com.dice_game.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dice_game.crud.dao.IPlayer;
import com.dice_game.crud.dto.Player;

@Service
public class PlayerService implements simpleCrud<Player> {
	
	@Autowired
	private IPlayer dao;

	@Override
	public Player saveOne(Player item) {
		return dao.save(item);
	}

	@Override
	public List<Player> readAll() {
		return dao.findAll();
	}

	@Override
	public Player readOne(String id) {
		return dao.findById(id).get();
	}

	@Override
	public Player updateOne(Player item) {
		return dao.save(item);
	}

	@Override
	public void deleteOne(String id) {
		dao.deleteById(id);
	}

}
