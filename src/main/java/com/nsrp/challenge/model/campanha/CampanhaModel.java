package com.nsrp.challenge.model.campanha;

import java.time.LocalDate;

public class CampanhaModel {

    private Long id;

    private String nome;

    private String timeDoCoracao;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private boolean ativa;

    public CampanhaModel() {
        super();
    }

    public CampanhaModel(Long id, String nome, String timeDoCoracao, LocalDate dataInicio, LocalDate dataFim, boolean ativa) {
        this.id = id;
        this.nome = nome;
        this.timeDoCoracao = timeDoCoracao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativa = ativa;
    }

    public CampanhaModel(String nome, String timeDoCoracao, LocalDate dataInicio, LocalDate dataFim) {
        this.nome = nome;
        this.timeDoCoracao = timeDoCoracao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTimeDoCoracao() {
        return timeDoCoracao;
    }

    public void setTimeDoCoracao(String timeDoCoracao) {
        this.timeDoCoracao = timeDoCoracao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}