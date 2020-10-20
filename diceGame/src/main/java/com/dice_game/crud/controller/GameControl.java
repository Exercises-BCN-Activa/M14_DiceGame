package com.dice_game.crud.controller;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dice_game.crud.dto.*;
import com.dice_game.crud.service.DiceService;
import com.dice_game.crud.service.PlayerService;

@RestController
@RequestMapping("/api")
public class GameControl {
	

	@Autowired
	private PlayerService PLAYER;

	@Autowired
	private DiceService DICE;


	@PostMapping("/players")
	public Player saveOne(@RequestBody Player player) {

		boolean verifyName = true;

		if (player.getName() != null && !player.getName().equalsIgnoreCase("unknown")
				&& !player.getName().equalsIgnoreCase("anonymous")) {

			for (Player p : PLAYER.readAll()) {

				if (p.getName().equalsIgnoreCase(player.getName())) {

					verifyName = false;
				}
			}
		}

		return (verifyName) ? PLAYER.saveOne(player) : new Player("IMPOSSIBLE: NAME ALREADY EXISTS");
	}

	@GetMapping("/players")
	public List<Player> readAll() {

		return PLAYER.readAll();
	}

	@GetMapping("/players/{id}")
	public Player readOne(@PathVariable(name = "id") Integer id) {

		return PLAYER.readOne(id);
	}

	@PutMapping("/players/{id}")
	public Player updateOne(@PathVariable(name = "id") Integer id, @RequestBody(required = false) Player pUpdate) {

		Player player = null;

		try {
			player = PLAYER.readOne(id);

		} catch (Exception e) {
			System.out.println("Player Not Founded!!!");
		}

		if (player != null) {
			if (pUpdate != null && pUpdate.getName() != null && !pUpdate.getName().isEmpty()
					&& !pUpdate.getName().equals(player.getName())) {

				player.setName(pUpdate.getName());
			}
			player.setStatus();

		}

		return (player != null) ? PLAYER.updateOne(player) : null;
	}

	@DeleteMapping("/players/{id}")
	public void deleteOne(@PathVariable(name = "id") Integer id) {

		PLAYER.deleteOne(id);
	}

	@PostMapping("/players/{id}/play")
	public Dice createOne(@PathVariable(name = "id") Integer id) {

		Player player = null;
		Dice dice = null;

		try {
			player = PLAYER.readOne(id);

		} catch (Exception e) {
			System.out.println("Player Not Founded!!!");
		}

		if (player != null) {
			dice = DICE.saveOne(new Dice(player));
			player.setStatus();
			PLAYER.updateOne(player);
		}

		return (player == null && dice == null) ? null : dice;
	}

	@GetMapping("/players/{id}/play")
	public List<Dice> readAllPlays(@PathVariable(name = "id") Integer id) {

		Player player = null;

		try {
			player = PLAYER.readOne(id);

		} catch (Exception e) {
			System.out.println("Player Not Founded!!!");
		}

		return player.getRounds();
	}

	@DeleteMapping("/players/{id}/play")
	public void deleteAllPlays(@PathVariable(name = "id") Integer id) {

		Player player = null;

		try {
			player = PLAYER.readOne(id);

		} catch (Exception e) {
			System.out.println("Player Not Founded!!!");
		}

		player.getRounds().stream().forEach(x -> DICE.deleteOne(x.getNumId()));

	}

	@GetMapping("/players/ranking")
	public String rankingValue() {

		Double rank = 0.0;
		
		int count = 0;

		for (Player p : PLAYER.readAll()) {

			if (p.getStatus() != null && !p.getRounds().isEmpty()) {

				rank += p.getStatus();
				count+=1;
			}
			
		}

		return new DecimalFormat("#.##").format(rank / count) + "%";
	}
	
	@GetMapping("/players/ranking/all")
	public List<Player> rankingAll() {
		
		List<Player> theList = PLAYER.readAll().stream()
									.filter(x -> x.getRounds().size()>0)
									.collect(Collectors.toList());
		
		theList.stream().forEach(x -> x.setStatus());
		
		theList.sort(Comparator.comparing(Player::getStatus).reversed());
		
		return theList;
		
	}
	
	@GetMapping("/players/ranking/winner")
	public List<Player> rankingWinner() {
		
		List<Player> theList = rankingAll();
		
		return theList.stream()
				.filter(x -> x.getStatus()==theList.get(0).getStatus())
				.collect(Collectors.toList());
		
	}
	
	@GetMapping("/players/ranking/loser")
	public List<Player> rankingLoser() {
		
		List<Player> theList = rankingAll();
		
		return theList.stream()
				.filter(x -> x.getStatus()==theList.get(theList.size()-1).getStatus())
				.collect(Collectors.toList());
		
	}

}
