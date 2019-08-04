package com.nsrp.challenge.service;

import com.nsrp.challenge.domain.Campanha;
import com.nsrp.challenge.service.campanha.CampanhaService;
import com.nsrp.challenge.service.campanha.CampanhaVigenciasUpdater;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDate.of;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CampanhaVigenciasUpdaterTest {

    @Mock
    private CampanhaService campanhaService;

    @InjectMocks
    private CampanhaVigenciasUpdater updater;

    private Campanha campanha;

    @Before
    public void setUp() {
        campanha = new Campanha();
        campanha.setId(1L);
        campanha.setDataInicioVigencia(of(2017, 10, 1));
        campanha.setDataFimVigencia(of(2017, 10, 3));
    }

    @Test
    public void updateCampanhaSemVigenciasNoPeriodo() {
        Mockito.when(campanhaService.findCampanhasAtivasPorPeriodoExcetoCampanhaId(any(LocalDate.class),
                any(LocalDate.class), Mockito.anyLong())).thenReturn(Collections.emptyList());
        updater.update(campanha);
        Mockito.verify(campanhaService, Mockito.never()).saveAll(Mockito.anyIterable());
    }

    /**
     * Cenário :
     * <p>
     * Campanha 1 : inicio dia 01/10/2017 a 03/10/2017;
     * Campanha 2: inicio dia 01/10/2017 a 02/10/2017;
     * Cadastrando Campanha 3: inicio 01/10/2017 a 03/10/2017;
     * -> Sistema:
     * Campanha 2 : 01/10/2017 a 03/10/2017 (porém a data bate com a campanha 1 e a 3, somando mais 1 dia)
     * Campanha 2 : 01/10/2017 a 04/10/2017
     * Campanha 1: 01/10/2017 a 04/10/2017 (bate com a data da campanha 2, somando mais 1 dia)
     * Campanha 1: 01/10/2017 a 05/10/2017
     * Incluindo campanha 3 : 01/10/2017 a 03/10/2017
     * <p>
     * Resultado esperado:
     * Campanha 1: inicio dia 01/10/2017 a 05/10/2017;
     * Campanha 2: inicio dia 01/10/2017 a 04/10/2017;
     * Campanha 3 (Sendo incluida) : inicio dia 01/10/2017 a 03/10/2017;
     */
    @Test
    public void updateCampanhaComVigenciasNoPeriodoComColisao() {
        List<Campanha> campanhas = new ArrayList<>();
        campanhas.add(createCampanha(of(2017, 10, 1), of(2017, 10, 3)));
        campanhas.add(createCampanha(of(2017, 10, 1), of(2017, 10, 2)));

        LocalDate dataInicio = campanha.getDataInicioVigencia();
        LocalDate dataFim = campanha.getDataFimVigencia();
        Long id = campanha.getId();
        Mockito.when(campanhaService.findCampanhasAtivasPorPeriodoExcetoCampanhaId(dataInicio, dataFim, id)).thenReturn(campanhas);

        updater.update(campanha);

        Mockito.verify(campanhaService, Mockito.times(1)).saveAll(campanhas);
        Assert.assertTrue(campanhas.get(0).getDataFimVigencia().isEqual(of(2017, 10, 5)));
        Assert.assertTrue(campanhas.get(1).getDataFimVigencia().isEqual(of(2017, 10, 4)));
    }

    /**
     * Cenário :
     * <p>
     * Campanha 1 : inicio dia 01/10/2017 a 04/10/2017;
     * Campanha 2: inicio dia 01/10/2017 a 07/10/2017;
     * Cadastrando Campanha 3: inicio 01/10/2017 a 08/10/2017;
     * -> Sistema:
     * Campanha 2 : 01/10/2017 a 08/10/2017 (porém a data bate com a campanha 3, somando mais 1 dia)
     * Campanha 2 : 01/10/2017 a 09/10/2017
     * Campanha 1: 01/10/2017 a 05/10/2017
     * Incluindo campanha 3 : 01/10/2017 a 07/10/2017
     * <p>
     * Resultado esperado:
     * Campanha 1: inicio dia 01/10/2017 a 06/10/2017;
     * Campanha 2: inicio dia 01/10/2017 a 09/10/2017;
     * Campanha 3 (Sendo incluida) : inicio dia 01/10/2017 a 08/10/2017;
     */
    @Test
    public void updateCampanhaComVigenciasNoPeriodoComColisaoUltimoRegistro() {
        List<Campanha> campanhas = new ArrayList<>();
        campanhas.add(createCampanha(of(2017, 10, 1), of(2017, 10, 4)));
        campanhas.add(createCampanha(of(2017, 10, 1), of(2017, 10, 7)));

        campanha.setDataFimVigencia(of(2017, 10, 8));
        LocalDate dataInicio = campanha.getDataInicioVigencia();
        LocalDate dataFim = campanha.getDataFimVigencia();
        Long id = campanha.getId();
        Mockito.when(campanhaService.findCampanhasAtivasPorPeriodoExcetoCampanhaId(dataInicio, dataFim, id)).thenReturn(campanhas);

        updater.update(campanha);

        Mockito.verify(campanhaService, Mockito.times(1)).saveAll(campanhas);
        Assert.assertTrue(campanhas.get(0).getDataFimVigencia().isEqual(of(2017, 10, 5)));
        Assert.assertTrue(campanhas.get(1).getDataFimVigencia().isEqual(of(2017, 10, 9)));
    }

    /**
     * Cenário :
     * <p>
     * Campanha 1 : inicio dia 01/10/2017 a 05/10/2017;
     * Campanha 2: inicio dia 01/10/2017 a 10/10/2017;
     * Cadastrando Campanha 3: inicio 01/10/2017 a 03/10/2017;
     * -> Sistema:
     * Nenhuma colisão encontrada após um dia adicionado na data final das vigências encontradas
     * Incluindo campanha 3 : 01/10/2017 a 03/10/2017
     * <p>
     * Resultado esperado:
     * Campanha 1: inicio dia 01/10/2017 a 06/10/2017;
     * Campanha 2: inicio dia 01/10/2017 a 11/10/2017;
     * Campanha 3 (Sendo incluida) : inicio dia 01/10/2017 a 03/10/2017;
     */
    @Test
    public void updateCampanhaComVigenciasNoPeriodoSemColisao() {
        List<Campanha> campanhas = new ArrayList<>();
        campanhas.add(createCampanha(of(2017, 10, 1), of(2017, 10, 5)));
        campanhas.add(createCampanha(of(2017, 10, 1), of(2017, 10, 10)));

        LocalDate dataInicio = campanha.getDataInicioVigencia();
        LocalDate dataFim = campanha.getDataFimVigencia();
        Long id = campanha.getId();
        Mockito.when(campanhaService.findCampanhasAtivasPorPeriodoExcetoCampanhaId(dataInicio, dataFim, id)).thenReturn(campanhas);

        updater.update(campanha);
        Mockito.verify(campanhaService, Mockito.times(1)).saveAll(campanhas);
        Assert.assertTrue(campanhas.get(0).getDataFimVigencia().isEqual(of(2017, 10, 6)));
        Assert.assertTrue(campanhas.get(1).getDataFimVigencia().isEqual(of(2017, 10, 11)));
    }

    private Campanha createCampanha(LocalDate dataInicio, LocalDate dataFim) {
        Campanha campanha = new Campanha();
        campanha.setDataInicioVigencia(dataInicio);
        campanha.setDataFimVigencia(dataFim);
        return campanha;
    }
}