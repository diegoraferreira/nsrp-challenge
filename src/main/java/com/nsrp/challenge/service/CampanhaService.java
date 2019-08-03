package com.nsrp.challenge.service;

import com.nsrp.challenge.domain.Campanha;
import com.nsrp.challenge.domain.Time;
import com.nsrp.challenge.model.CampanhaModel;
import com.nsrp.challenge.repository.CampanhaRepository;
import com.nsrp.challenge.validation.PeriodoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CampanhaService {

    @Autowired
    private TimeService timeService;

    @Autowired
    private CampanhaRepository repository;

    @Autowired
    private PeriodoValidator periodoValidator;

    @Transactional
    public void save(CampanhaModel campanhaModel) {
        periodoValidator.validate(campanhaModel.getDataInicio(), campanhaModel.getDataFim());

        Campanha campanha = new Campanha();
        campanha.setNome(campanhaModel.getNome());
        campanha.setDataInicioVigencia(campanhaModel.getDataInicio());
        campanha.setDataFimVigencia(campanhaModel.getDataFim());

        Optional<Time> timeDoCoracaoOptional = timeService.findByNome(campanhaModel.getTimeDoCoracao());
        if (timeDoCoracaoOptional.isPresent()) {
            campanha.setTimeDoCoracao(timeDoCoracaoOptional.get());
        } else {
            Time timeDoCoracao = timeService.save(campanhaModel.getTimeDoCoracao());
            campanha.setTimeDoCoracao(timeDoCoracao);
        }

        this.repository.save(campanha);
    }

    @Transactional
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}