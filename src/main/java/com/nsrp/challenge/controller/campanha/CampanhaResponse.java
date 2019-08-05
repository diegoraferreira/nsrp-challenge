package com.nsrp.challenge.controller.campanha;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Campanha Response")
public class CampanhaResponse {

    private String nomeCampanha;

    private Long idTimeCoracao;

    private String dataVigencia;

    public CampanhaResponse() {
        super();
    }

    public CampanhaResponse(String nomeCampanha, Long idTimeCoracao, String dataVigencia) {
        this.nomeCampanha = nomeCampanha;
        this.idTimeCoracao = idTimeCoracao;
        this.dataVigencia = dataVigencia;
    }

    @ApiModelProperty(value = "Nome da campanha")
    public String getNomeCampanha() {
        return nomeCampanha;
    }

    public void setNomeCampanha(String nomeCampanha) {
        this.nomeCampanha = nomeCampanha;
    }

    @ApiModelProperty(value = "Id do time do coração")
    public Long getIdTimeCoracao() {
        return idTimeCoracao;
    }

    public void setIdTimeCoracao(Long idTimeCoracao) {
        this.idTimeCoracao = idTimeCoracao;
    }

    @ApiModelProperty(value = "Data início vigência - Data fim vigência")
    public String getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(String dataVigencia) {
        this.dataVigencia = dataVigencia;
    }
}