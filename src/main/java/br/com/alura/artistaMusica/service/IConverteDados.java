package br.com.alura.artistaMusica.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
