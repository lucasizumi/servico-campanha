package com.challenge.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.challenge.campanha.controller.TimeController;
import com.challenge.campanha.exception.RestResponseEntityExceptionHandler;
import com.challenge.campanha.model.Time;
import com.challenge.campanha.service.TimeService;

@RunWith(MockitoJUnitRunner.class)
public class TimeControllerTest {

	@Mock
	private TimeService service;
	
	@InjectMocks
	private TimeController controller;
	
	@InjectMocks
	private RestResponseEntityExceptionHandler exceptionHandler;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(exceptionHandler).build();
	}
	
	@Test
    public void findByName() throws Exception {
		
		Time time = new Time();
		time.setId(1L);
		time.setNome("Time de Teste");
		
		Mockito.when(service.buscarPorNome(Mockito.anyString())).thenReturn(time);
		
		String json = "{\"id\" : \"1\"," +
                "\"nome\":\"Time de Teste\"}";
		
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/time/list/nome/{nome}", "Time de Teste")
        		.content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Time de Teste"));
    }
	
	@Test
    public void findById() throws Exception {
		
		Time time = new Time();
		time.setId(1L);
		time.setNome("Time de Teste");
		
		Mockito.when(service.buscarPorId(Mockito.anyLong())).thenReturn(time);
		
		String json = "{\"id\" : \"1\"," +
                "\"nome\":\"Time de Teste\"}";
		
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/time/list/id/{id}", 1L)
        		.content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Time de Teste"));
    }
	
}
