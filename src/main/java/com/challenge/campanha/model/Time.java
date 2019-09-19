package com.challenge.campanha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_TIME")
@SequenceGenerator(name = "seqTime", sequenceName = "SEQ_TIME")
public class Time {

	@Id
	@GeneratedValue(generator = "seqTime", strategy = GenerationType.SEQUENCE)
	@Column(name = "cd_time", nullable = false, updatable = false)
	private Long id;

	@Column(name = "nm_time", nullable = false)
	private String nome;

	public Time() {
		super();
	}

	public Time(String nome) {
		super();
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
