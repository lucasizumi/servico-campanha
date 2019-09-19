package com.challenge.campanha.dto;

import java.time.LocalDate;

public class CampanhaDto {

	private Long id;
	
	private String nome;
	
	private String time;
	
	private LocalDate dataInicioVigencia;
	
	private LocalDate dataFimVigencia;

	public CampanhaDto() {
		super();
	}

	public CampanhaDto(String nome, String time, LocalDate dataInicioVigencia, LocalDate dataFimVigencia) {
		super();
		this.nome = nome;
		this.time = time;
		this.dataInicioVigencia = dataInicioVigencia;
		this.dataFimVigencia = dataFimVigencia;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public LocalDate getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(LocalDate dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public LocalDate getDataFimVigencia() {
		return dataFimVigencia;
	}

	public void setDataFimVigencia(LocalDate dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}
	
}
