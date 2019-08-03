package com.nsrp.challenge.service;

import com.nsrp.challenge.domain.Time;
import com.nsrp.challenge.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TimeService {

    @Autowired
    private TimeRepository timeRepository;

    @Transactional
    public void save(Time time) {
        this.timeRepository.save(time);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return this.timeRepository.existsById(id);
    }
}
