package com.challenge.campanha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.campanha.model.Time;
import com.challenge.campanha.repository.TimeRepository;

@Service
public class TimeService {

	private TimeRepository timeRepository;

	@Autowired
	public TimeService(TimeRepository timeRepository) {
		this.timeRepository = timeRepository;
	}

	@Transactional
	public Time cadastrar(String nome) {
		Time time = new Time(nome);
		return this.timeRepository.save(time);
	}
	
	@Transactional(readOnly = true)
	public Time buscarPorNome(String nome) {
		return this.timeRepository.buscarPorNome(nome);
	}
	
	@Transactional(readOnly = true)
	public Time buscarPorId(Long id) {
		return this.timeRepository.buscarPorId(id);
	}

}
