package com.nsrp.challenge.service;

import com.nsrp.challenge.domain.Time;
import com.nsrp.challenge.repository.TimeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimeServiceTest {

    private static final String TIME_DO_CORACAO = "TIME_DO_CORACAO";

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private TimeService timeService;

    @Test
    public void save() {
        timeService.save(TIME_DO_CORACAO);
        Mockito.verify(timeRepository, Mockito.times(1)).save(Mockito.any(Time.class));
    }

    @Test
    public void findByNome() {
        timeService.findByNome(TIME_DO_CORACAO);
        Mockito.verify(timeRepository, Mockito.times(1)).findByNome(TIME_DO_CORACAO);
    }
}