package br.com.alura.artistaMusica.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosMusica(
			@JsonAlias("Nome") String titulo,
	        @JsonAlias("Album") String album) {
}
