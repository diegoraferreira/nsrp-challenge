package com.nsrp.challenge.repository;

import com.nsrp.challenge.domain.Time;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends CrudRepository<Time, Long> {
}
