package com.nsrp.challenge.service.campanha;

import com.nsrp.challenge.domain.Campanha;
import com.nsrp.challenge.domain.Time;
import com.nsrp.challenge.model.campanha.CampanhaModel;
import com.nsrp.challenge.repository.CampanhaRepository;
import com.nsrp.challenge.service.TimeService;
import com.nsrp.challenge.validation.PeriodoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.nsrp.challenge.utils.NsrpChallengeDateUtils.format;

@Service
public class CampanhaService {

    @Autowired
    private TimeService timeService;

    @Autowired
    private CampanhaRepository repository;

    @Autowired
    private PeriodoValidator periodoValidator;

    @Autowired
    private CampanhaUpdateProducer campanhaUpdateProducer;

    @Transactional
    public Campanha save(CampanhaModel campanhaModel) {
        periodoValidator.validate(campanhaModel.getDataInicio(), campanhaModel.getDataFim());

        Campanha campanha = new Campanha();
        campanha.setNome(campanhaModel.getNome());
        campanha.setDataInicioVigencia(campanhaModel.getDataInicio());
        campanha.setDataFimVigencia(campanhaModel.getDataFim());
        campanha.setAtiva(true);
        campanha.setTimeDoCoracao(this.findOrCreateTimeDoCoracao(campanhaModel.getTimeDoCoracao()));

        try {
            this.repository.save(campanha);
            return campanha;
        } catch (DataIntegrityViolationException e) {
            String msg = "Já existe uma campanha cadastrada com o nome '%s'";
            throw new DataIntegrityViolationException(String.format(msg, campanhaModel.getNome()), e);
        }
    }

    @Transactional
    public void saveAll(Iterable<Campanha> campanhas) {
        this.repository.saveAll(campanhas);
    }

    @Transactional
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Transactional
    public void update(CampanhaModel campanhaModel) {
        Optional<Campanha> campanhaOptional = this.repository.findById(campanhaModel.getId());
        String timeDoCoracao = campanhaModel.getTimeDoCoracao();
        if (!campanhaOptional.isPresent()) {
            String msg = "Nenhuma campanha encontrada, parâmetros informados: Nome: %s, Time do coração: %s, " +
                    "Data inicio: %s, Data fim: %s";
            String dataInicio = format(campanhaModel.getDataInicio());
            String dataFim = format(campanhaModel.getDataFim());
            throw new EntityNotFoundException(String.format(msg, campanhaModel.getNome(), timeDoCoracao, dataInicio, dataFim));
        } else {
            Campanha campanha = campanhaOptional.get();
            campanha.setNome(campanhaModel.getNome());
            campanha.setDataInicioVigencia(campanhaModel.getDataInicio());
            campanha.setDataFimVigencia(campanhaModel.getDataFim());
            campanha.setAtiva(campanhaModel.isAtiva());
            campanha.setTimeDoCoracao(this.findOrCreateTimeDoCoracao(timeDoCoracao));

            this.repository.save(campanha);
            this.campanhaUpdateProducer.sendMenssage(campanhaModel);
        }
    }

    @Transactional(readOnly = true)
    public List<CampanhaModel> findAllCampanhasVigentes() {
        return this.repository.findAllCampanhasVigentes(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<CampanhaModel> findCampanhasVigentesPorTimeDoCoracao(Long id) {
        return this.repository.findCampanhasVigentesPorTimeDoCoracao(id, LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<Campanha> findCampanhasAtivasPorPeriodoExcetoCampanhaId(LocalDate dataInicio, LocalDate dataFim, Long id) {
        return repository.findCampanhasAtivasPorPeriodoExcetoCampanhaId(dataInicio, dataFim, id);
    }

    private Time findOrCreateTimeDoCoracao(String timeDoCoracao) {
        Optional<Time> timeDoCoracaoOptional = timeService.findByNome(timeDoCoracao);
        if (timeDoCoracaoOptional.isPresent()) {
            return timeDoCoracaoOptional.get();
        } else {
            return timeService.save(timeDoCoracao);
        }
    }
}