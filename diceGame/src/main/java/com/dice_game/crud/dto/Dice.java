package com.dice_game.crud.dto;

import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dice")
public class Dice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer numId;

	@Column(name = "reg")
	private final Calendar register = Calendar.getInstance();

	private final Integer value1 = (int) Math.ceil(Math.random() * 6);

	private final Integer value2 = (int) Math.ceil(Math.random() * 6);

	private final boolean status = (value1 + value2 == 7) ? true : false;

	@ManyToOne
	private Player player;

	public Dice() {
	}

	public Dice(Player player) {
		this.player = player;
	}

	public Integer getNumId() {
		return numId;
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

	public Calendar getRegister() {
		return register;
	}

	@JsonIgnore
	public Player getPlayer() {
		return player;
	}

	public void setNumId(Integer numId) {
		if (this.numId == null) {
			this.numId = numId;
		}
	}

	public void setPlayer(Player player) {
		if (this.player == null) {
			this.player = player;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(numId, player, register, status, value1, value2);
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
		return Objects.equals(numId, other.numId) && Objects.equals(player, other.player)
				&& Objects.equals(register, other.register) && status == other.status
				&& Objects.equals(value1, other.value1) && Objects.equals(value2, other.value2);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dice [numId=");
		builder.append(numId);
		builder.append(", value1=");
		builder.append(value1);
		builder.append(", value2=");
		builder.append(value2);
		builder.append(", status=");
		builder.append(status);
		builder.append(", register=");
		builder.append(register);
		builder.append(", player=");
		builder.append(player);
		builder.append("]");
		return builder.toString();
	}

}
