package com.nsrp.challenge.validation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ValidationException;
import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class PeriodoValidatorTest {

    @InjectMocks
    private PeriodoValidator validator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validateDataInicioAnteriorDataFim() {
        validator.validate(LocalDate.of(2019, 2, 2), LocalDate.of(2019, 2, 10));
    }

    @Test
    public void validateDataInicioSuperiorDataFim() {
        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("A data fim (10/02/2019) não pode ser anterior a data fim (02/02/2019)");

        validator.validate(LocalDate.of(2019, 2, 10), LocalDate.of(2019, 2, 2));
    }
}