package com.dice_game.crud.dto;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "player")
public class Player {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer numId;

	private String name = "unknown";

	@Column(name = "reg", updatable = false)
	private final Calendar register = Calendar.getInstance();

	@Column(name = "status")
	private Double status;

	@OneToMany(orphanRemoval = true, targetEntity = Dice.class, mappedBy = "player", cascade=CascadeType.REMOVE)
	private List<Dice> rounds;

	public Player() {
	}

	public Player(String name) {
		setName(name);
	}

	public Integer getNumId() {
		return numId;
	}

	public String getName() {
		return name;
	}

	public Calendar getRegister() {
		return register;
	}

	@JsonIgnore
	public List<Dice> getRounds() {
		return rounds;
	}
	
	public Double getStatus() {
		return status;
	}

	public void setNumId(Integer numId) {
		if (this.numId == null) {
			this.numId = numId;
		}
	}

	public void setName(String name) {
		this.name = (name == null || name.isEmpty()) ? "anonymous" : name;
	}

	public void setRounds(List<Dice> rounds) {
		this.rounds = rounds;
	}

	public void setStatus() {
		DecimalFormat f = new DecimalFormat("#.##");
		status = (rounds.isEmpty()) ? 0
				: Double.parseDouble(f.format(
						(double) rounds.stream()
						.filter(x -> x.getStatus() == true)
						.count() / rounds.size() * 100)
						.replaceAll(",", "."));
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, numId, register, rounds, status);
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
		return Objects.equals(name, other.name) && Objects.equals(numId, other.numId)
				&& Objects.equals(register, other.register) && Objects.equals(rounds, other.rounds)
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Player [numId=");
		builder.append(numId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", register=");
		builder.append(register);
		builder.append(", status=");
		builder.append(status);
		builder.append(", rounds=");
		builder.append(rounds);
		builder.append("]");
		return builder.toString();
	}

}
