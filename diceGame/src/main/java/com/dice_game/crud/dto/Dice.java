package com.dice_game.crud.dto;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * DTO class that configures the dice rolls of each user
 * 
 * @author FaunoGuazina
 *
 */
@Document(collection = "dice")
public class Dice {

	@Id
	private String _id;

	//establish date of creation for every user
	private Date register = new Date(System.currentTimeMillis());

	//creation of the value of the first dice
	private Integer value1 = (int) Math.ceil(Math.random() * 6);

	//creation of the value of the second dice
	private Integer value2 = (int) Math.ceil(Math.random() * 6);

	//checks if the sum of the dices is seven
	private boolean status = (value1 + value2 == 7) ? true : false;

	@DBRef(db = "player") //notation that sets up as a foreign key
	private Player player;

	public Dice() {
	}

	public Dice(Player player) {
		this.player = player;
	}

	public Integer getValue1() {
		return value1;
	}

	public Integer getValue2() {
		return value2;
	}

	public boolean getStatus() {
		return status;
	}

	public Date getRegister() {
		return register;
	}

	public Player getPlayer() {
		return player;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public void setPlayer(Player player) {
		if (this.player == null) {
			this.player = player;
		}
	}

	public void setRegister() {
		if (register == null) {
			register = new Date(System.currentTimeMillis());
		}
	}

	public void setValue1() {
		if (value1 == null) {
			value1 = (int) Math.ceil(Math.random() * 6);
		}
	}

	public void setValue2() {
		if (value2 == null) {
			value2 = (int) Math.ceil(Math.random() * 6);
		}
	}

	public void setStatus() {
		status = (value1 + value2 == 7) ? true : false;
	}

	@Override
	public String toString() {
		return "'Dice' : {'_id' : '" + _id + "'" 
				+ ", 'register' : '" + register + "'" 
				+ ", 'value1' : '" + value1 + "'"
				+ ", 'value2' : '" + value2 + "'"
				+ ", 'status' : '" + status + "'"
				+ "," + player.toString() + "}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(_id, player, register, status, value1, value2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Dice other = (Dice) obj;
		return Objects.equals(_id, other._id) && Objects.equals(player, other.player)
				&& Objects.equals(register, other.register) && status == other.status
				&& Objects.equals(value1, other.value1) && Objects.equals(value2, other.value2);
	}
	
	

}
