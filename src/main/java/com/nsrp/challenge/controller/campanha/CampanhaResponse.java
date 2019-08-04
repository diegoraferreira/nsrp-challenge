package com.nsrp.challenge.controller.campanha;

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

    public String getNomeCampanha() {
        return nomeCampanha;
    }

    public void setNomeCampanha(String nomeCampanha) {
        this.nomeCampanha = nomeCampanha;
    }

    public Long getIdTimeCoracao() {
        return idTimeCoracao;
    }

    public void setIdTimeCoracao(Long idTimeCoracao) {
        this.idTimeCoracao = idTimeCoracao;
    }

    public String getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(String dataVigencia) {
        this.dataVigencia = dataVigencia;
    }
}