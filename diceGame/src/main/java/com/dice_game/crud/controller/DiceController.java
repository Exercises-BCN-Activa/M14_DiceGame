package com.dice_game.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.Response;
import com.dice_game.crud.view.implementation.DiceService;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/rounds")
public class DiceController {
	
	@Autowired
	private DiceService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createNewRoundOfDices(@RequestBody PlayerJson playerJson) {
		return service.createRound(playerJson);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Response findRoundsOfPlayer(@RequestBody PlayerJson playerJson) {
		return service.readRoundsByPlayer(playerJson);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Response deleteRoundsOfPlayer(@RequestBody PlayerJson playerJson) {
		return service.deleteRoundsByPlayer(playerJson);
	}
	
	@DeleteMapping("/all")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ADMIN')")
	public Response deleteAllRoundsOfGame(@RequestBody PlayerJson playerJson) {
		return service.deleteRoundsByPlayer(playerJson);
	}

}
