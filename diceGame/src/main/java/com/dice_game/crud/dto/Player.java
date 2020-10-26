package com.dice_game.crud.dto;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "player")
public class Player {

	@Id
	private String _id;

	private String name = "unknown";

	private Date register = new Date(System.currentTimeMillis());

	private Double status;

	public Player() {
	}

	public Player(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public Date getRegister() {
		return register;
	}
	
	@JsonIgnore
	public Double getStatus() {
		return status;
	}
	
	public String getScore() {
		return (status==null) ? "never played" : status + "% victories";
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public void setRegister() {
		if (register == null) {
			register = new Date(System.currentTimeMillis());
		}
	}

	public void setName(String name) {
		this.name = (name == null || name.isEmpty()) ? "anonymous" : name;
	}

	public void setStatus(List<Dice> rounds) {
		status = (rounds.isEmpty()) ? 0							// if have no dices in the list return zero
						: Double.parseDouble(					// parse to Double because decimal format return string
							new DecimalFormat("#.##").format(	// create new decimal format to adjust de decimal status
							(double) rounds.stream()			// casting to double (count method return long) and stream the list of dices
							.filter(x -> x.getStatus() == true)	// filter out only won games
							.count() 							// then return the amount of the won dices 
							/ rounds.size() * 100)				// then divide by number total of dices
							.replaceAll(",", "."));				// then replace the commas to dots
	}

	@Override
	public String toString() {
		return "Player [_id=" + _id 
				+ ", name=" + name 
				+ ", register=" + register 
				+ ", status=" + status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_id, name, register, status);
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
		Player other = (Player) obj;
		return Objects.equals(_id, other._id) && Objects.equals(name, other.name)
				&& Objects.equals(register, other.register) && Objects.equals(status, other.status);
	}
	
	
	
}
