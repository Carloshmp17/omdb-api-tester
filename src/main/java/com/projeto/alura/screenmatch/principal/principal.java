package com.projeto.alura.screenmatch.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.projeto.alura.screenmatch.model.DadosEpisodios;
import com.projeto.alura.screenmatch.model.DadosSerie;
import com.projeto.alura.screenmatch.model.DadosTemporada;
import com.projeto.alura.screenmatch.model.Episodio;
import com.projeto.alura.screenmatch.service.ConsumoApi;
import com.projeto.alura.screenmatch.service.ConverteDados;

public class principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=863e5770";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void menu(){
        System.out.println("Digite o nome da serie: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<=dados.totalTemporadas(); i++){
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		// temporadas.forEach(System.out::println);

        // for(int i = 0; i < dados.totalTemporadas(); i++){
        //     List<DadosEpisodios> episodiosTemporada = temporadas.get(i).episodios();
        //     for(int j = 0; j < episodiosTemporada.size(); j++){
        //         System.out.println(episodiosTemporada.get(j).titulo());
        //     }
        // }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        // REFERENCIA
        // temporadas.ForEach(System.out::println)  é o mesmo que temporadas.ForEach(t -> System.out.println(t))

        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
        .flatMap(t -> t.episodios().stream())
        .collect(Collectors.toList());

        System.out.println("\nTop 5 episodios");
        dadosEpisodios.stream()
        .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
        .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
        .limit(5)
        .forEach(System.out::println);

        // DICA
        // O metodo peek() pode ser utilizado para ajudar a visualizar melhor as etapas de processamento do codigo (Debug)

        List<Episodio> episodios = temporadas.stream()
        .flatMap(t -> t.episodios().stream()
        .map(d -> new Episodio(t.numero(), d))
        ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        // System.out.println("Digite um titulo: ");
        // var trechoTitulo = leitura.nextLine();
        // Optional será um conteiner que irá armazenar um episodio caso ele exista 
        // Optional <Episodio> episodioBuscado = episodios.stream().filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
        // .findFirst();

        // if(episodioBuscado.isPresent()){
        //     System.out.println("Episodio encontrado!");
        //     System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        // } else{
        //     System.out.println("Episodio não encontrado!");
        // }

        // System.out.println("Digite uma data: ");
        // var ano = leitura.nextInt();
        // leitura.nextLine();

        // LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        // DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // episodios.stream().filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca)).forEach(e -> System.out.println(
        //     "Temporada: " + e.getTemporada() +
        //     "\nEpisodio: " + e.getTitulo() +
        //     "\nData de laçamento: " + e.getDataLancamento().format(formatador)
        // ));

        Map <Integer, Double> avaliacoesPorTemporada = episodios.stream().filter(e -> e.getAvaliacao() > 0.0).collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

    }
}
