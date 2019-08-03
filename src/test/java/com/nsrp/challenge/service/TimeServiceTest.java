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

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private TimeService timeService;

    @Test
    public void save() {
        Time time = Mockito.mock(Time.class);
        timeService.save(time);
        Mockito.verify(timeRepository, Mockito.times(1)).save(time);
    }

    @Test
    public void existsById() {
        Long id = 1L;
        timeService.existsById(id);
        Mockito.verify(timeRepository, Mockito.times(1)).existsById(id);
    }
}