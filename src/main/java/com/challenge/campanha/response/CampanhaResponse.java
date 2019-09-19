package com.challenge.campanha.response;

public class CampanhaResponse {

	private String nome;

    private Long idTime;

    private String dataVigencia;

	public CampanhaResponse() {
		super();
	}

	public CampanhaResponse(String nome, Long idTime, String dataVigencia) {
		super();
		this.nome = nome;
		this.idTime = idTime;
		this.dataVigencia = dataVigencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdTime() {
		return idTime;
	}

	public void setIdTime(Long idTime) {
		this.idTime = idTime;
	}

	public String getDataVigencia() {
		return dataVigencia;
	}

	public void setDataVigencia(String dataVigencia) {
		this.dataVigencia = dataVigencia;
	}
    
}
