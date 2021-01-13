package com.dice_game.crud.view.service;

import com.dice_game.crud.model.dto.PlayerJson;
import com.dice_game.crud.utilities.Response;

public interface PlayerDetailService {
	
	public Response createOne(PlayerJson playerJson);
	public Response readAll(PlayerJson playerJson);
	public Response readOne(PlayerJson playerJson);
	public Response updateOne(PlayerJson playerJson);
	public Response deleteOne(PlayerJson playerJson);
	public Response deleteAll(PlayerJson playerJson);

}
