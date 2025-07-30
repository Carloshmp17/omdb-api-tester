package com.projeto.alura.screenmatch.model;

import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;

    public Serie(DadosSerie dados){
        this.titulo = dados.titulo();
        this.totalTemporadas = dados.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dados.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dados.genero().split(",")[0].trim());
        this.atores = dados.atores();
        this.poster = dados.poster();
        this.sinopse = dados.sinopse();
    }
}
