package com.dice_game.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dice_game.crud.dao.IDice;
import com.dice_game.crud.dto.Dice;
import com.dice_game.crud.dto.Player;

/**
 * Class that implements the use of the DAO class and allows queries in the database.
 * 
 * @author FaunoGuazina
 *
 */
@Service
public class DiceService implements simpleCrud<Dice>{
	
	@Autowired
	private IDice dao;

	@Override
	public Dice saveOne(Dice item) {
		return dao.save(item);
	}

	@Override
	public List<Dice> readAll() {
		return dao.findAll();
	}

	@Override
	public Dice readOne(String id) {
		return dao.findById(id).get();
	}

	@Override
	public Dice updateOne(Dice item) {
		return dao.save(item);
	}

	@Override
	public void deleteOne(String id) {
		dao.deleteById(id);
	}

	public void delete(Dice dice) {
		dao.delete(dice);
	}
	
	public List<Dice> readByPlayer(Player player) {
		return dao.findByPlayer(player);
	}

}
