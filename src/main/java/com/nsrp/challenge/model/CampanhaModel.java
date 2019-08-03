package com.nsrp.challenge.model;

import java.time.LocalDate;

public class CampanhaModel {

    private String nome;

    private String timeDoCoracao;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    public CampanhaModel() {
        super();
    }

    public CampanhaModel(String nome, String timeDoCoracao, LocalDate dataInicio, LocalDate dataFim) {
        this.nome = nome;
        this.timeDoCoracao = timeDoCoracao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
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
}