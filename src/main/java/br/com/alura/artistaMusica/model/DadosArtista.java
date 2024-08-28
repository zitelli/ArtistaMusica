package br.com.alura.artistaMusica.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosArtista(
		@JsonAlias("Nome") String nome,
        @JsonAlias("Tipo") String tipo) {
}
