package com.nsrp.challenge.repository;

import com.nsrp.challenge.domain.Campanha;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampanhaRepository extends CrudRepository<Campanha, Long> {

    @Query("SELECT campanha " +
            "FROM Campanha campanha WHERE " +
            "campanha.dataInicioVigencia >= :dataInicio AND " +
            "campanha.dataFimVigencia <= :dataFim AND " +
            "campanha.id <> :id AND " +
            "campanha.ativa = true")
    List<Campanha> findCampanhasAtivasPorPeriodoExcetoCampanhaId(@Param("dataInicio") LocalDate dataInicio,
                                                                 @Param("dataFim") LocalDate dataFim,
                                                                 @Param("id") Long id);
}