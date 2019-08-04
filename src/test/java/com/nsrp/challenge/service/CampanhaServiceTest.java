package com.nsrp.challenge.service;

import com.nsrp.challenge.domain.Campanha;
import com.nsrp.challenge.domain.Time;
import com.nsrp.challenge.model.CampanhaModel;
import com.nsrp.challenge.repository.CampanhaRepository;
import com.nsrp.challenge.validation.PeriodoValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

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

    @InjectMocks
    private CampanhaService campanhaService;

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
        Mockito.verify(campanhaRepository, Mockito.times(1)).save(campanhaArgumentCaptor.capture());
        this.validateCampanha(campanhaArgumentCaptor.getValue(), timeDoCoracao);
    }

    @Test
    public void saveTimeNaoExistente() {
        Mockito.when(timeService.findByNome(TIME_DO_CORACAO)).thenReturn(Optional.empty());
        Mockito.when(timeService.save(TIME_DO_CORACAO)).thenReturn(timeDoCoracao);

        campanhaService.save(campanhaModel);

        Mockito.verify(periodoValidator, Mockito.times(1)).validate(DATA_INICIO, DATA_FIM);
        Mockito.verify(timeService, Mockito.times(1)).save(TIME_DO_CORACAO);
        Mockito.verify(campanhaRepository, Mockito.times(1)).save(campanhaArgumentCaptor.capture());
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

    private void validateCampanha(Campanha campanha, Time timeDoCoracao) {
        Assert.assertNotNull(campanha);
        Assert.assertEquals(NOME_CAMPANHA, campanha.getNome());
        Assert.assertEquals(campanha.getTimeDoCoracao(), timeDoCoracao);
        Assert.assertEquals(campanha.getDataInicioVigencia(), DATA_INICIO);
        Assert.assertEquals(campanha.getDataFimVigencia(), DATA_FIM);
        Assert.assertTrue(campanha.isAtiva());
    }
}