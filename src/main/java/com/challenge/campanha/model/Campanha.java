package com.challenge.campanha.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CAMPANHA")
@SequenceGenerator(name = "seqCampanha", sequenceName = "SEQ_CAMPANHA")
public class Campanha {

	@Id
	@GeneratedValue(generator = "seqCampanhas", strategy = GenerationType.SEQUENCE)
	@Column(name = "cd_campanha", nullable = false, updatable = false)
	private Long id;

	@Column(name="nm_campanha", nullable = false)
	private String nome;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_time", nullable = false, foreignKey = @ForeignKey(name = "FK_TIME_CAMPANHA"))
	private Time time;

	@Column(name="dt_inicio_vigencia", nullable = false)
	private LocalDate dataInicioVigencia;

	@Column(name="dt_fim_vigencia", nullable = false)
	private LocalDate dataFimVigencia;

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

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
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
