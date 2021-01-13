package com.dice_game.crud.model.dto;

import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "dice")
@JsonIgnoreProperties(value = "player", allowSetters = true)
public final class Dice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date registration;

	private int value1;

	private int value2;

	@ManyToOne
	private Player player;

	public Dice() {
	}

	public DiceJson toJson() {
		return DiceJson.from(this);
	}

	public static Dice newRound(Player player) {
		Dice dice = new Dice();
		dice.setPlayer(player);
		dice.rollDices();
		return dice;
	}
	
	private void rollDices() {
		this.setValue1(randonNumberFrom1To6());
		this.setValue2(randonNumberFrom1To6());
	}

	private static int randonNumberFrom1To6() {
		return IntStream.generate(() -> new Random().nextInt(6) + 1).limit(36).findAny().getAsInt();
	}

	public boolean isWon() {
		return (value1 + value2 == 7) ? true : false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRegistration() {
		return registration;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public Integer getValue1() {
		return value1;
	}

	public void setValue1(Integer value1) {
		this.value1 = value1;
	}

	public Integer getValue2() {
		return value2;
	}

	public void setValue2(Integer value2) {
		this.value2 = value2;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, player, registration, value1, value2);
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
		return Objects.equals(id, other.id) && Objects.equals(player, other.player)
				&& Objects.equals(registration, other.registration) && value1 == other.value1 && value2 == other.value2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dice : {id : ");
		builder.append(id);
		builder.append(", registration : ");
		builder.append(registration);
		builder.append(", value1 : ");
		builder.append(value1);
		builder.append(", value2 : ");
		builder.append(value2);
		builder.append(", player : ");
		builder.append(player.getId());
		builder.append(", isWon : ");
		builder.append(isWon());
		builder.append("}");
		return builder.toString();
	}

}
