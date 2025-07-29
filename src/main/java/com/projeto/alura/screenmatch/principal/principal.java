package com.projeto.alura.screenmatch.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.projeto.alura.screenmatch.model.DadosSerie;
import com.projeto.alura.screenmatch.model.DadosTemporada;
import com.projeto.alura.screenmatch.service.ConsumoApi;
import com.projeto.alura.screenmatch.service.ConverteDados;

public class principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=863e5770";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void menu(){
        var menu = """
                1 - Buscar series
                2 - Buscar episodios 

                0 - Sair
                """;

        System.out.println(menu);
        var opcao =leitura.nextInt(); 
        leitura.nextLine();

        switch (opcao) {
            case 1:
                buscarSerieWeb();
                break;
            case 2: 
                buscarEpisodioPorSerie();
                break;
            case 0:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção invalida");
        }

        

        
    }
    
    private void buscarSerieWeb(){
        DadosSerie dados = getDadosSerie();
        System.out.println(dados);
    }
        
    private DadosSerie getDadosSerie(){
        System.out.println("Digite o nome da serie para buscar: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for(int i = 1; i <= dadosSerie.totalTemporadas(); i++){
            var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+" + API_KEY));
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);
    }
}
