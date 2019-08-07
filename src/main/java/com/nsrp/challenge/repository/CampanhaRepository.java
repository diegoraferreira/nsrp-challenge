package com.nsrp.challenge.repository;

import com.nsrp.challenge.domain.Campanha;
import com.nsrp.challenge.model.campanha.CampanhaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long> {

    @Query("SELECT campanha " +
            "FROM Campanha campanha WHERE " +
            "campanha.dataInicioVigencia >= :dataInicio AND " +
            "campanha.dataFimVigencia <= :dataFim AND " +
            "campanha.id <> :id AND " +
            "campanha.ativa = true")
    List<Campanha> findCampanhasAtivasPorPeriodoExcetoCampanhaId(@Param("dataInicio") LocalDate dataInicio,
                                                                 @Param("dataFim") LocalDate dataFim,
                                                                 @Param("id") Long id);

    @Query(" SELECT new com.nsrp.challenge.model.campanha.CampanhaModel (" +
            "   campanha.id, campanha.nome, timeDoCoracao.nome, campanha.dataInicioVigencia, " +
            "   campanha.dataFimVigencia, campanha.ativa " +
            "   ) " +
            "FROM Campanha campanha " +
            "JOIN campanha.timeDoCoracao timeDoCoracao " +
            "WHERE campanha.dataFimVigencia >= :dataFim " +
            "ORDER BY campanha.nome, campanha.dataFimVigencia DESC")
    List<CampanhaModel> findAllCampanhasVigentes(@Param("dataFim") LocalDate dataFim);

    @Query(" SELECT new com.nsrp.challenge.model.campanha.CampanhaModel (" +
            "   campanha.id, campanha.nome, timeDoCoracao.nome, campanha.dataInicioVigencia, " +
            "   campanha.dataFimVigencia, campanha.ativa " +
            "   ) " +
            "FROM Campanha campanha " +
            "JOIN campanha.timeDoCoracao timeDoCoracao " +
            "WHERE campanha.dataFimVigencia >= :dataFim " +
            "AND timeDoCoracao.id = :idTimeDoCoracao " +
            "ORDER BY campanha.nome, campanha.dataFimVigencia DESC")
    List<CampanhaModel> findCampanhasVigentesPorTimeDoCoracao(@Param("idTimeDoCoracao") Long id, @Param("dataFim") LocalDate dataFim);
}