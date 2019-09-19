package com.challenge.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import com.challenge.campanha.dto.CampanhaDto;
import com.challenge.campanha.model.Campanha;
import com.challenge.campanha.model.Time;
import com.challenge.campanha.repository.CampanhaRepository;
import com.challenge.campanha.service.CampanhaService;
import com.challenge.campanha.service.TimeService;
import com.challenge.campanha.validation.CampanhaValidator;

@RunWith(MockitoJUnitRunner.class)
public class CampanhaServiceTest {

    private static final String NOME_CAMPANHA = "Campanha de Teste";
    
    private static final String TIME = "Time Teste";

    private static final LocalDate DATA_INICIO = LocalDate.of(2019, 9, 17);

    private static final LocalDate DATA_FIM = LocalDate.of(2019, 9 , 20);

    @Mock
    private CampanhaRepository campanhaRepository;
    
    @Mock
    private TimeService timeService;

    @Mock
    private CampanhaValidator validator;

    @InjectMocks
    private CampanhaService campanhaService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    private ArgumentCaptor<Campanha> campanhaArgumentCaptor;

    @Mock
    private Time time;

    private static CampanhaDto campanhaDto;

    @BeforeClass
    public static void setUp() {
    	campanhaDto = new CampanhaDto(NOME_CAMPANHA, TIME, DATA_INICIO, DATA_FIM);
    }

    @Test
    public void cadastrar() {
        Mockito.when(timeService.buscarPorNome(TIME)).thenReturn(time);
        Mockito.when(campanhaRepository.save(Mockito.any(Campanha.class))).thenThrow(DataIntegrityViolationException.class);

        expectedException.expectMessage("Já existe uma campanha cadastrada com o nome " + NOME_CAMPANHA + ".");
        expectedException.expect(DataIntegrityViolationException.class);

        campanhaService.cadastrar(campanhaDto);
    }

    @Test
    public void cadastrarOuAlterarTodos() {
        List<Campanha> campanhas = Collections.singletonList(Mockito.mock(Campanha.class));
        campanhaService.cadastrarOuAlterarTodos(campanhas);
        Mockito.verify(campanhaRepository, Mockito.times(1)).saveAll(campanhas);
    }

    @Test
    public void delete() {
        Long id = 1L;
        campanhaService.deletar(id);
        Mockito.verify(campanhaRepository, Mockito.times(1)).deleteById(id);
    }


    @Test
    public void buscarTodos() {
    	campanhaService.buscarTodasCampanhasVigentes();
        Mockito.verify(campanhaRepository, Mockito.times(1)).buscarTodasCampanhasVigentes(Mockito.any(LocalDate.class));
    }
    
    @Test
    public void buscarCampanhasPorPeriodo() {
    	campanhaService.buscarCampanhasPorPeriodo(DATA_INICIO, DATA_FIM);
        Mockito.verify(campanhaRepository, Mockito.times(1)).buscarCampanhasPorPeriodo(DATA_INICIO, DATA_FIM);
    }

    @Test
    public void buscarCampanhasPorTime() {
    	campanhaService.buscarCampanhasVigentesPorTime(TIME);
        Mockito.verify(campanhaRepository, Mockito.times(1)).buscarCampanhasVigentesPorTime(TIME, LocalDate.now());
    }
    
}
