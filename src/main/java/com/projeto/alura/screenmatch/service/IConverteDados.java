package com.projeto.alura.screenmatch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class <T> classe);
    
}
