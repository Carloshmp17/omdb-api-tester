package com.projeto.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


// A diferença entre o @JsonAlias e o @JsonPropery é que o Json property está relacionado tanto a leitura como a escrita de um Json, já o Alias está relacionado somente a leitura.
@JsonIgnoreProperties(ignoreUnknown=true)
public record DadosSerie(@JsonAlias("Title") String titulo,@JsonAlias("totalSeasons") Integer totalTemporadas,@JsonAlias("imdbRating") String avaliacao) {

}
