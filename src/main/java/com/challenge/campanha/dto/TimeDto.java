package com.challenge.campanha.dto;

public class TimeDto {

	private Long id;
	
	private String nome;

	public TimeDto() {
		super();
	}

	public TimeDto(String nome) {
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
