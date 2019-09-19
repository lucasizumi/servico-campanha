package com.challenge.campanha.validation;

import java.time.LocalDate;

import javax.validation.ValidationException;

import org.springframework.stereotype.Component;

@Component
public class CampanhaValidator {

	public void validarPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        if (dataFim.isBefore(dataInicio)) {
            throw new ValidationException("A data de inicio " + dataInicio + " da campanha não pode ser menor que a data final " + dataFim + ".");
        }
    }
	
}
