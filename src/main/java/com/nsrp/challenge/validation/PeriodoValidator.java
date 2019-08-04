package com.nsrp.challenge.validation;

import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.time.LocalDate;

import static com.nsrp.challenge.config.NsrpChallengeDateUtils.format;

@Component
public class PeriodoValidator {

    public void validate(LocalDate dataInicio, LocalDate dataFim) {
        if (dataFim.isBefore(dataInicio)) {
            String message = "A data fim (%s) não pode ser anterior a data fim (%s)";
            throw new ValidationException(String.format(message, format(dataInicio), format(dataFim)));
        }
    }
}