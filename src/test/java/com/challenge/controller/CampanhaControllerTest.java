package com.challenge.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.challenge.campanha.controller.CampanhaController;
import com.challenge.campanha.dto.CampanhaDto;
import com.challenge.campanha.exception.RestResponseEntityExceptionHandler;
import com.challenge.campanha.model.Campanha;
import com.challenge.campanha.model.Time;
import com.challenge.campanha.service.CampanhaService;

@RunWith(MockitoJUnitRunner.class)
public class CampanhaControllerTest {

	@Mock
    private CampanhaService service;

    @InjectMocks
    private CampanhaController controller;
	
    @InjectMocks
    private RestResponseEntityExceptionHandler exceptionHandler;
    
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(exceptionHandler).build();
    }

    @Test
    public void create() throws Exception {
        Campanha campanha = new Campanha();
        campanha.setNome("Campanha Teste");
        
        Time timeMock = Mockito.mock(Time.class);
        Mockito.when(timeMock.getId()).thenReturn(1L);
        
        campanha.setTime(timeMock);
        campanha.setDataInicioVigencia(LocalDate.of(2019, 9, 16));
        campanha.setDataFimVigencia(LocalDate.of(2019, 9, 20));
        
        Mockito.when(service.cadastrar(Mockito.any(CampanhaDto.class))).thenReturn(campanha);

        String json = "{\"nome\":\"Campanha Teste\", " +
                "\"time\":\"Time de Teste\", " +
                "\"dataInicio\":\"2019-09-16\", " +
                "\"dataFim\":\"2019-09-20\"}";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/campanha")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Campanha Teste"))
                .andExpect(jsonPath("$.idTime").value("1"))
                .andExpect(jsonPath("$.dataVigencia").value("16/09/2019 - 20/09/2019"));
    }
    
    @Test
    public void update() throws Exception {
        String json = "{\"id\" : \"1\"," +
                "\"nome\":\"Campanha Teste\", " +
                "\"time\":\"Time de Teste\", " +
                "\"dataInicioVigencia\":\"2019-09-16\", " +
                "\"dataFimVigencia\":\"2019-09-20\"}";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/campanha")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Campanha Teste"))
                .andExpect(jsonPath("$.time").value("Time de Teste"))
                .andExpect(jsonPath("$.dataInicioVigencia[0]").value("2019"))
                .andExpect(jsonPath("$.dataInicioVigencia[1]").value("9"))
                .andExpect(jsonPath("$.dataInicioVigencia[2]").value("16"))
                .andExpect(jsonPath("$.dataFimVigencia[0]").value("2019"))
                .andExpect(jsonPath("$.dataFimVigencia[1]").value("9"))
                .andExpect(jsonPath("$.dataFimVigencia[2]").value("20"));
    }
    
    @Test
    public void delete() throws Exception {
        Long campanhaId = 1L;
        Mockito.doNothing().when(service).deletar(campanhaId);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/campanha/{id}", campanhaId));
        resultActions.andExpect(status().isOk());
    }
    
    @Test
    public void list() throws Exception {
        CampanhaDto campanha1 = new CampanhaDto();
        campanha1.setId(1L);
        campanha1.setNome("Campanha Teste 1");
        campanha1.setDataInicioVigencia(LocalDate.of(2019, 9, 16));
        campanha1.setDataFimVigencia(LocalDate.of(2019, 9, 20));
        campanha1.setTime("Time de Teste 1");

        CampanhaDto campanha2 = new CampanhaDto();
        campanha2.setId(2L);
        campanha2.setNome("Campanha Teste 2");
        campanha2.setDataInicioVigencia(LocalDate.of(2019, 9, 17));
        campanha2.setDataFimVigencia(LocalDate.of(2019, 9, 21));
        campanha2.setTime("Time de Teste 2");

        Mockito.when(service.buscarTodasCampanhasVigentes()).thenReturn(Arrays.asList(campanha1, campanha2));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/campanha/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nome").value("Campanha Teste 1"))
                .andExpect(jsonPath("$[0].time").value("Time de Teste 1"))
                .andExpect(jsonPath("$[0].dataInicioVigencia[0]").value("2019"))
                .andExpect(jsonPath("$[0].dataInicioVigencia[1]").value("9"))
                .andExpect(jsonPath("$[0].dataInicioVigencia[2]").value("16"))
                .andExpect(jsonPath("$[0].dataFimVigencia[0]").value("2019"))
                .andExpect(jsonPath("$[0].dataFimVigencia[1]").value("9"))
                .andExpect(jsonPath("$[0].dataFimVigencia[2]").value("20"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].nome").value("Campanha Teste 2"))
                .andExpect(jsonPath("$[1].time").value("Time de Teste 2"))
                .andExpect(jsonPath("$[1].dataInicioVigencia[0]").value("2019"))
                .andExpect(jsonPath("$[1].dataInicioVigencia[1]").value("9"))
                .andExpect(jsonPath("$[1].dataInicioVigencia[2]").value("17"))
                .andExpect(jsonPath("$[1].dataFimVigencia[0]").value("2019"))
                .andExpect(jsonPath("$[1].dataFimVigencia[1]").value("9"))
                .andExpect(jsonPath("$[1].dataFimVigencia[2]").value("21"));
    }
    
    @Test
    public void findByTeam() throws Exception {
        CampanhaDto campanha = new CampanhaDto();
        campanha.setId(1L);
        campanha.setNome("Campanha Teste");
        campanha.setDataInicioVigencia(LocalDate.of(2019, 9, 16));
        campanha.setDataFimVigencia(LocalDate.of(2019, 9, 20));
        campanha.setTime("Time de Teste");

        Mockito.when(service.buscarCampanhasVigentesPorTime("Time de Teste")).thenReturn(Arrays.asList(campanha));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/campanha/list/time/{time}", "Time de Teste")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nome").value("Campanha Teste"))
                .andExpect(jsonPath("$[0].time").value("Time de Teste"))
                .andExpect(jsonPath("$[0].dataInicioVigencia[0]").value("2019"))
                .andExpect(jsonPath("$[0].dataInicioVigencia[1]").value("9"))
                .andExpect(jsonPath("$[0].dataInicioVigencia[2]").value("16"))
                .andExpect(jsonPath("$[0].dataFimVigencia[0]").value("2019"))
                .andExpect(jsonPath("$[0].dataFimVigencia[1]").value("9"))
                .andExpect(jsonPath("$[0].dataFimVigencia[2]").value("20"));
    }
    
}
