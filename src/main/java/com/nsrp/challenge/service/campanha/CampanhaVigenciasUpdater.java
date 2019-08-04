package com.nsrp.challenge.service.campanha;

import com.nsrp.challenge.domain.Campanha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Atualizador responsável por garantir que não haverá colisão de data final de vigência entre a campanha sendo
 * cadastrada e as campanhas vigentes no periodo
 * </p>
 * <p>
 * Premissas:
 *     <ul>
 *         <li>Será acrescido um dia em todas as vigências encontradas de forma inclusiva no período de cadastro da nova vigência</li>
 *         <li>Após adicionado um dia, caso exista colisão, será adicionado um dia na campanha que causou a colisão de datas</li>
 *     </ul>
 * </p>
 */
@Component
public class CampanhaVigenciasUpdater {

    @Autowired
    private CampanhaService campanhaService;

    public void update(Campanha campanha) {
        LocalDate dataInicio = campanha.getDataInicioVigencia();
        LocalDate dataFim = campanha.getDataFimVigencia();
        List<Campanha> campanhas = campanhaService.findCampanhasAtivasPorPeriodoExcetoCampanhaId(dataInicio, dataFim, campanha.getId());

        if (!campanhas.isEmpty()) {
            // Adiciona um dia em todas as campanhas vigentes no periodo
            campanhas.forEach(c -> c.setDataFimVigencia(c.getDataFimVigencia().plusDays(1)));

            boolean hasColision = true;
            Campanha campanhaEmColisao = campanha;
            while (hasColision) {
                for (int i = 0; i < campanhas.size(); i++) {
                    Campanha campanhaVigencia = campanhas.get(i);
                    boolean isEquals = campanhaVigencia.getDataFimVigencia().isEqual(campanhaEmColisao.getDataFimVigencia());
                    // Verifica se os valores são iguais mas não atualiza caso o registro já tenha sido atualizado por estar em colisão anteriormente
                    if (isEquals && !(campanhaVigencia == campanhaEmColisao)) {
                        campanhaVigencia.setDataFimVigencia(campanhaVigencia.getDataFimVigencia().plusDays(1));
                        campanhaEmColisao = campanhaVigencia;
                        hasColision = true;
                    } else {
                        hasColision = false;
                    }
                }
            }

            campanhaService.saveAll(campanhas);
        }
    }
}