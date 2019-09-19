package com.challenge.campanha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.campanha.dto.CampanhaDto;
import com.challenge.campanha.model.Campanha;
import com.challenge.campanha.response.CampanhaResponse;
import com.challenge.campanha.service.CampanhaService;
import com.challenge.campanha.utils.DateFormatter;

@RestController
@RequestMapping("/campanha")
public class CampanhaController {

	@Autowired
	private CampanhaService service;

	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CampanhaResponse> create(@RequestBody CampanhaDto campanhaDto) {

		Campanha campanha = this.service.cadastrar(campanhaDto);

		String nome = campanha.getNome();
		Long idTime = campanha.getTime().getId();
		String dataVigencia = DateFormatter.format(campanha.getDataInicioVigencia()) + " - "
				+ DateFormatter.format(campanha.getDataFimVigencia());
		
		CampanhaResponse campanhaResponse = new CampanhaResponse(nome, idTime, dataVigencia);
		return ResponseEntity.status(HttpStatus.CREATED).body(campanhaResponse);
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CampanhaDto> update(@RequestBody CampanhaDto campanhaDto) {
		this.service.alterar(campanhaDto);
		return ResponseEntity.ok(campanhaDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.deletar(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/list")
	public List<CampanhaDto> list() {
		return this.service.buscarTodasCampanhasVigentes();
	}
	
	@GetMapping("list/time/{time}")
	public List<CampanhaDto> findByTeam(@PathVariable("time") String time) {
		return this.service.buscarCampanhasVigentesPorTime(time);
	}

}
