package com.nsrp.challenge.repository;

import com.nsrp.challenge.domain.Campanha;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampanhaRepository extends CrudRepository<Campanha, Long> {
}