package com.nsrp.challenge.validation;

import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
public class PeriodoValidator {

    private static final String DATE_PATTERN = "dd/MM/YYYY";

    public void validate(LocalDate dataInicio, LocalDate dataFim) {
        if (dataFim.isBefore(dataInicio)) {
            String message = "A data fim (%s) não pode ser anterior a data fim (%s)";
            DateTimeFormatter formatter = ofPattern(DATE_PATTERN);
            throw new ValidationException(String.format(message, formatter.format(dataInicio), formatter.format(dataFim)));
        }
    }
}