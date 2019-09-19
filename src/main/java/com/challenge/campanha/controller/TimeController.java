package com.challenge.campanha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.campanha.model.Time;
import com.challenge.campanha.service.TimeService;

@RestController
@RequestMapping("/time")
public class TimeController {

	@Autowired
	private TimeService service;
	
	@GetMapping(value = "/list/nome/{nome}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Time> findByName(@PathVariable("nome") String nome) {
		Time time = this.service.buscarPorNome(nome);
		
		if(time != null) {
			return ResponseEntity.ok().body(time);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/list/id/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Time> findById(@PathVariable("id") Long id) {
		Time time = this.service.buscarPorId(id);
		
		if(time != null) {
			return ResponseEntity.ok().body(time);
		}
		
		return ResponseEntity.notFound().build();
	}
}
