package com.nsrp.challenge.service;

import com.nsrp.challenge.domain.Campanha;
import com.nsrp.challenge.domain.Time;
import com.nsrp.challenge.model.campanha.CampanhaModel;
import com.nsrp.challenge.repository.CampanhaRepository;
import com.nsrp.challenge.service.campanha.CampanhaService;
import com.nsrp.challenge.service.campanha.CampanhaUpdateProducer;
import com.nsrp.challenge.validation.PeriodoValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CampanhaServiceTest {

    private static final String TIME_DO_CORACAO = "TIME_DO_CORACAO";

    private static final String NOME_CAMPANHA = "CAMPANHA";

    private static final LocalDate DATA_INICIO = LocalDate.of(2019, 2, 1);

    private static final LocalDate DATA_FIM = LocalDate.of(2019, 2, 10);

    @Mock
    private TimeService timeService;

    @Mock
    private CampanhaRepository campanhaRepository;

    @Mock
    private PeriodoValidator periodoValidator;

    @Mock
    private CampanhaUpdateProducer campanhaUpdateProducer;

    @InjectMocks
    private CampanhaService campanhaService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    private ArgumentCaptor<Campanha> campanhaArgumentCaptor;

    @Mock
    private Time timeDoCoracao;

    private static CampanhaModel campanhaModel;

    @BeforeClass
    public static void setUp() {
        campanhaModel = new CampanhaModel(NOME_CAMPANHA, TIME_DO_CORACAO, DATA_INICIO, DATA_FIM);
    }

    @Test
    public void saveTimeExistente() {
        Mockito.when(timeService.findByNome(TIME_DO_CORACAO)).thenReturn(Optional.of(timeDoCoracao));

        campanhaService.save(campanhaModel);

        Mockito.verify(periodoValidator, Mockito.times(1)).validate(DATA_INICIO, DATA_FIM);
        Mockito.verify(timeService, Mockito.never()).save(TIME_DO_CORACAO);
        Mockito.verify(campanhaRepository, Mockito.times(1)).saveAndFlush(campanhaArgumentCaptor.capture());
        this.validateCampanha(campanhaArgumentCaptor.getValue(), timeDoCoracao);
    }

    @Test
    public void saveCampanhaExistente() {
        Mockito.when(timeService.findByNome(TIME_DO_CORACAO)).thenReturn(Optional.of(timeDoCoracao));
        Mockito.when(campanhaRepository.saveAndFlush(Mockito.any(Campanha.class))).thenThrow(DataIntegrityViolationException.class);

        expectedException.expectMessage("Já existe uma campanha cadastrada com o nome 'CAMPANHA'");
        expectedException.expect(DataIntegrityViolationException.class);

        campanhaService.save(campanhaModel);
    }

    @Test
    public void saveTimeNaoExistente() {
        Mockito.when(timeService.findByNome(TIME_DO_CORACAO)).thenReturn(Optional.empty());
        Mockito.when(timeService.save(TIME_DO_CORACAO)).thenReturn(timeDoCoracao);

        campanhaService.save(campanhaModel);

        Mockito.verify(periodoValidator, Mockito.times(1)).validate(DATA_INICIO, DATA_FIM);
        Mockito.verify(timeService, Mockito.times(1)).save(TIME_DO_CORACAO);
        Mockito.verify(campanhaRepository, Mockito.times(1)).saveAndFlush(campanhaArgumentCaptor.capture());
        this.validateCampanha(campanhaArgumentCaptor.getValue(), timeDoCoracao);
    }

    @Test
    public void saveAll() {
        List<Campanha> campanhas = Collections.singletonList(Mockito.mock(Campanha.class));
        campanhaService.saveAll(campanhas);
        Mockito.verify(campanhaRepository, Mockito.times(1)).saveAll(campanhas);
    }

    @Test
    public void delete() {
        Long id = 1L;
        campanhaService.delete(id);
        Mockito.verify(campanhaRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void updateCampanhaEncontrada() {
        CampanhaModel model = new CampanhaModel();
        model.setId(1L);
        model.setDataInicio(LocalDate.of(2019, 10, 5));
        model.setDataFim(LocalDate.of(2019, 10, 10));
        model.setNome("CAMPANHA ABC");
        model.setTimeDoCoracao("TIME 1");
        Time timeMock = Mockito.mock(Time.class);
        Mockito.when(timeMock.getNome()).thenReturn("TIME 1");
        Mockito.when(timeService.findByNome("TIME 1")).thenReturn(Optional.of(timeMock));
        Mockito.when(campanhaRepository.findById(model.getId())).thenReturn(Optional.of(Mockito.spy(Campanha.class)));

        campanhaService.update(model);

        Mockito.verify(campanhaRepository, Mockito.times(1)).save(campanhaArgumentCaptor.capture());
        Campanha campanha = campanhaArgumentCaptor.getValue();
        Mockito.verify(campanhaUpdateProducer, Mockito.times(1)).sendMenssage(model);

        Assert.assertNotNull(campanha);
        Assert.assertEquals(model.getNome(), campanha.getNome());
        Assert.assertEquals(model.getTimeDoCoracao(), campanha.getTimeDoCoracao().getNome());
        Assert.assertEquals(model.getDataInicio(), campanha.getDataInicioVigencia());
        Assert.assertEquals(model.getDataFim(), campanha.getDataFimVigencia());
        Assert.assertEquals(model.isAtiva(), campanha.isAtiva());
    }

    @Test
    public void updateCampanhaNaoEncontrada() {
        CampanhaModel model = new CampanhaModel();
        model.setId(1L);
        model.setDataInicio(LocalDate.of(2019, 10, 5));
        model.setDataFim(LocalDate.of(2019, 10, 10));
        model.setNome("CAMPANHA ABC");
        model.setTimeDoCoracao("TIME 1");
        Mockito.when(campanhaRepository.findById(model.getId())).thenReturn(Optional.empty());

        String msg = "Nenhuma campanha encontrada, parâmetros informados: Nome: CAMPANHA ABC, Time do coração: TIME 1, " +
                "Data inicio: 05/10/2019, Data fim: 10/10/2019";
        expectedException.expectMessage(msg);
        expectedException.expect(EntityNotFoundException.class);

        campanhaService.update(model);
    }

    @Test
    public void findAllTest() {
        campanhaService.findAllCampanhasVigentes();
        Mockito.verify(campanhaRepository, Mockito.times(1)).findAllCampanhasVigentes(Mockito.any(LocalDate.class));
    }

    @Test
    public void findAllCampanhasVigentesPorTimeDoCoracao() {
        campanhaService.findCampanhasVigentesPorTimeDoCoracao(1L);
        Mockito.verify(campanhaRepository, Mockito.times(1)).findCampanhasVigentesPorTimeDoCoracao(1L, LocalDate.now());
    }

    private void validateCampanha(Campanha campanha, Time timeDoCoracao) {
        Assert.assertNotNull(campanha);
        Assert.assertEquals(NOME_CAMPANHA, campanha.getNome());
        Assert.assertEquals(campanha.getTimeDoCoracao(), timeDoCoracao);
        Assert.assertEquals(campanha.getDataInicioVigencia(), DATA_INICIO);
        Assert.assertEquals(campanha.getDataFimVigencia(), DATA_FIM);
        Assert.assertTrue(campanha.isAtiva());
    }
}