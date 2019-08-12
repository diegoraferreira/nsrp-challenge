package com.nsrp.challenge.controller.campanha;

import com.nsrp.challenge.domain.Campanha;
import com.nsrp.challenge.domain.Time;
import com.nsrp.challenge.exceptionhandler.RestResponseEntityExceptionHandler;
import com.nsrp.challenge.model.campanha.CampanhaModel;
import com.nsrp.challenge.service.campanha.CampanhaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CampanhaControllerTest {

    @Mock
    private CampanhaService campanhaService;

    @InjectMocks
    private RestResponseEntityExceptionHandler exceptionHandler;

    @InjectMocks
    private CampanhaController campanhaController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(campanhaController).setControllerAdvice(exceptionHandler).build();
    }

    @Test
    public void create() throws Exception {
        Campanha campanha = new Campanha();
        campanha.setNome("Campanha1");
        Time timeMock = Mockito.mock(Time.class);
        Mockito.when(timeMock.getId()).thenReturn(10L);
        campanha.setTimeDoCoracao(timeMock);
        campanha.setDataInicioVigencia(LocalDate.of(2019, 8, 1));
        campanha.setDataFimVigencia(LocalDate.of(2019, 8, 4));
        Mockito.when(campanhaService.save(Mockito.any(CampanhaModel.class))).thenReturn(campanha);

        String json = "{\"nome\":\"Campanha1\", " +
                "\"timeDoCoracao\":\"Time do coração\", " +
                "\"dataInicio\":\"2019-08-01\", " +
                "\"dataFim\":" +
                "\"2019-08-04\"}";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/campanha")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeCampanha").value("Campanha1"))
                .andExpect(jsonPath("$.idTimeCoracao").value("10"))
                .andExpect(jsonPath("$.dataVigencia").value("01/08/2019 - 04/08/2019"));
    }

    @Test
    public void update() throws Exception {
        String json = "{\"id\" : \"1\"," +
                "\"nome\":\"Campanha1\", " +
                "\"timeDoCoracao\":\"Time do coração\", " +
                "\"dataInicio\":\"2019-08-01\", " +
                "\"dataFim\":\"2019-08-04\"}";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/campanha")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Campanha1"))
                .andExpect(jsonPath("$.timeDoCoracao").value("Time do coração"))
                .andExpect(jsonPath("$.dataInicio[0]").value("2019"))
                .andExpect(jsonPath("$.dataInicio[1]").value("8"))
                .andExpect(jsonPath("$.dataInicio[2]").value("1"))
                .andExpect(jsonPath("$.dataFim[0]").value("2019"))
                .andExpect(jsonPath("$.dataFim[1]").value("8"))
                .andExpect(jsonPath("$.dataFim[2]").value("4"));
    }

    @Test
    public void list() throws Exception {
        CampanhaModel campanha1 = new CampanhaModel();
        campanha1.setId(1L);
        campanha1.setNome("Campanha 1");
        campanha1.setDataInicio(LocalDate.of(2019, 2, 1));
        campanha1.setDataFim(LocalDate.of(2019, 2, 10));
        campanha1.setTimeDoCoracao("Time do coração 1");
        campanha1.setAtiva(true);

        CampanhaModel campanha2 = new CampanhaModel();
        campanha2.setId(2L);
        campanha2.setNome("Campanha 2");
        campanha2.setDataInicio(LocalDate.of(2019, 3, 1));
        campanha2.setDataFim(LocalDate.of(2019, 3, 10));
        campanha2.setTimeDoCoracao("Time do coração 2");
        campanha2.setAtiva(true);

        Mockito.when(campanhaService.findAllCampanhasVigentes()).thenReturn(Arrays.asList(campanha1, campanha2));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/campanha/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nome").value("Campanha 1"))
                .andExpect(jsonPath("$[0].timeDoCoracao").value("Time do coração 1"))
                .andExpect(jsonPath("$[0].dataInicio[0]").value("2019"))
                .andExpect(jsonPath("$[0].dataInicio[1]").value("2"))
                .andExpect(jsonPath("$[0].dataInicio[2]").value("1"))
                .andExpect(jsonPath("$[0].dataFim[0]").value("2019"))
                .andExpect(jsonPath("$[0].dataFim[1]").value("2"))
                .andExpect(jsonPath("$[0].dataFim[2]").value("10"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].nome").value("Campanha 2"))
                .andExpect(jsonPath("$[1].timeDoCoracao").value("Time do coração 2"))
                .andExpect(jsonPath("$[1].dataInicio[0]").value("2019"))
                .andExpect(jsonPath("$[1].dataInicio[1]").value("3"))
                .andExpect(jsonPath("$[1].dataInicio[2]").value("1"))
                .andExpect(jsonPath("$[1].dataFim[0]").value("2019"))
                .andExpect(jsonPath("$[1].dataFim[1]").value("3"))
                .andExpect(jsonPath("$[1].dataFim[2]").value("10"));
    }

    @Test
    public void findByTimeDoCoracao() throws Exception {
        CampanhaModel campanha1 = new CampanhaModel();
        campanha1.setId(1L);
        campanha1.setNome("Campanha 1");
        campanha1.setDataInicio(LocalDate.of(2019, 2, 1));
        campanha1.setDataFim(LocalDate.of(2019, 2, 10));
        campanha1.setTimeDoCoracao("Time");
        campanha1.setAtiva(true);

        Mockito.when(campanhaService.findCampanhasVigentesPorTimeDoCoracao("Time")).thenReturn(Arrays.asList(campanha1));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/campanha/list/timedocoracao/{timeDoCoracao}", "Time")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].nome").value("Campanha 1"))
                .andExpect(jsonPath("$[0].timeDoCoracao").value("Time"))
                .andExpect(jsonPath("$[0].dataInicio[0]").value("2019"))
                .andExpect(jsonPath("$[0].dataInicio[1]").value("2"))
                .andExpect(jsonPath("$[0].dataInicio[2]").value("1"))
                .andExpect(jsonPath("$[0].dataFim[0]").value("2019"))
                .andExpect(jsonPath("$[0].dataFim[1]").value("2"))
                .andExpect(jsonPath("$[0].dataFim[2]").value("10"));
    }

    @Test
    public void delete() throws Exception {
        Long camapanhaId = 1L;
        Mockito.doNothing().when(campanhaService).delete(camapanhaId);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/campanha/{id}", camapanhaId));
        resultActions.andExpect(status().isOk());
    }
}