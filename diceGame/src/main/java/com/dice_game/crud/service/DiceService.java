package com.dice_game.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dice_game.crud.dao.IDice;
import com.dice_game.crud.dto.Dice;

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
	public Dice readOne(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Dice updateOne(Dice item) {
		return dao.save(item);
	}

	@Override
	public void deleteOne(Integer id) {
		dao.deleteById(id);
	}

}
