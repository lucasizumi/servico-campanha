package com.challenge.campanha.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.campanha.dto.CampanhaDto;
import com.challenge.campanha.model.Campanha;
import com.challenge.campanha.model.Time;
import com.challenge.campanha.repository.CampanhaRepository;
import com.challenge.campanha.validation.CampanhaValidator;

@Service
public class CampanhaService {

	private CampanhaRepository repository;

	private CampanhaValidator validator;

	private TimeService timeService;

	private CampanhaUpdateAlerter campanhaUpdateAlerter;

	@Autowired
	public CampanhaService(CampanhaRepository repository, CampanhaValidator validator, TimeService timeService,
			CampanhaUpdateAlerter campanhaUpdateAlerter) {
		this.repository = repository;
		this.validator = validator;
		this.timeService = timeService;
		this.campanhaUpdateAlerter = campanhaUpdateAlerter;
	}

	@Transactional
	public Campanha cadastrar(CampanhaDto campanhaDto) {
		validator.validarPeriodo(campanhaDto.getDataInicioVigencia(), campanhaDto.getDataFimVigencia());

		Campanha campanha = mapeiaCampanha(campanhaDto);

		try {

			verificaEAlteraSeNecessarioPeriodoCampanhas(campanha);
			this.repository.save(campanha);
			return campanha;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(
					"Já existe uma campanha cadastrada com o nome " + campanha.getNome() + ".");
		}
	}

	@Transactional
	public void cadastrarOuAlterarTodos(Iterable<Campanha> campanhas) {
		this.repository.saveAll(campanhas);
	}

	@Transactional(readOnly = true)
	public List<CampanhaDto> buscarTodasCampanhasVigentes() {
		return this.repository.buscarTodasCampanhasVigentes(LocalDate.now());
	}

	@Transactional(readOnly = true)
	public List<Campanha> buscarCampanhasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		return repository.buscarCampanhasPorPeriodo(dataInicio, dataFim);
	}

	@Transactional(readOnly = true)
	public List<CampanhaDto> buscarCampanhasVigentesPorTime(String time) {
		return this.repository.buscarCampanhasVigentesPorTime(time, LocalDate.now());
	}

	public void alterar(CampanhaDto campanhaDto) {
		Optional<Campanha> campanhaEncontrada = this.repository.findById(campanhaDto.getId());

		if (!campanhaEncontrada.isPresent()) {
			throw new EntityNotFoundException("Campanha " + campanhaDto.getNome() + " não encontrada.");
		} else {
			Campanha campanha = mapeiaCampanha(campanhaDto);

			this.repository.save(campanha);
			this.campanhaUpdateAlerter.sendMenssage(campanhaDto);
		}
	}

	@Transactional
	public void deletar(Long id) {
		this.repository.deleteById(id);
	}

	private Time buscaOuCriaTime(String nome) {
		Time time = timeService.buscarPorNome(nome);

		if (null != time) {
			return time;
		} else {
			return timeService.cadastrar(nome);
		}
	}

	private void verificaEAlteraSeNecessarioPeriodoCampanhas(Campanha campanha) {
		List<Campanha> campanhasEncontradas = buscarCampanhasPorPeriodo(campanha.getDataInicioVigencia(),
				campanha.getDataFimVigencia());

		if (!campanhasEncontradas.isEmpty()) {
			campanhasEncontradas.forEach(camp -> camp.setDataFimVigencia(camp.getDataFimVigencia().plusDays(1)));

			Campanha campanhaConflitada = campanha;

			boolean temConflito = Boolean.TRUE;
			while (temConflito) {
				for (Campanha campanhaEncontrada : campanhasEncontradas) {
					boolean ehIgual = campanhaConflitada.getDataFimVigencia()
							.isEqual(campanhaEncontrada.getDataFimVigencia());
					if (ehIgual && campanhaEncontrada != campanhaConflitada) {
						campanhaConflitada = campanhaEncontrada;
						campanhaEncontrada.setDataFimVigencia(campanhaEncontrada.getDataFimVigencia().plusDays(1));
						temConflito = Boolean.TRUE;
					} else {
						temConflito = Boolean.FALSE;
					}
				}
			}

			cadastrarOuAlterarTodos(campanhasEncontradas);
			this.campanhaUpdateAlerter.sendMenssage(campanhasEncontradas);
		}
	}

	private Campanha mapeiaCampanha(CampanhaDto campanhaDto) {
		Campanha campanha = new Campanha();
		campanha.setNome(campanhaDto.getNome());
		campanha.setTime(buscaOuCriaTime(campanhaDto.getTime()));
		campanha.setDataInicioVigencia(campanhaDto.getDataInicioVigencia());
		campanha.setDataFimVigencia(campanhaDto.getDataFimVigencia());

		return campanha;
	}
}
