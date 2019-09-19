package com.challenge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.challenge.campanha.model.Time;
import com.challenge.campanha.repository.TimeRepository;
import com.challenge.campanha.service.TimeService;

@RunWith(MockitoJUnitRunner.class)
public class TimeServiceTest {

    private static final String TIME = "Time Teste";

    @Mock
    private TimeRepository repository;

    @InjectMocks
    private TimeService timeService;

    @Test
    public void cadastrar() {
        timeService.cadastrar(TIME);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Time.class));
    }
    
    @Test
    public void buscarPorId() {
        timeService.buscarPorId(1L);
        Mockito.verify(repository, Mockito.times(1)).buscarPorId(1L);
    }

    @Test
    public void buscarPorNome() {
        timeService.buscarPorNome(TIME);
        Mockito.verify(repository, Mockito.times(1)).buscarPorNome(TIME);
    }
    
}
