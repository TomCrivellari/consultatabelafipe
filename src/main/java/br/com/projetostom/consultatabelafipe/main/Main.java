package br.com.projetostom.consultatabelafipe.main;

import br.com.projetostom.consultatabelafipe.model.Dados;
import br.com.projetostom.consultatabelafipe.model.Modelos;
import br.com.projetostom.consultatabelafipe.model.Veiculo;
import br.com.projetostom.consultatabelafipe.service.ConsumoAPI;
import br.com.projetostom.consultatabelafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        System.out.println("**** OPÇÕES ****\n" +
                "carros\n" +
                "motos\n" +
                "caminhoes\n\n" +
                "Digite uma das opções acima para a consulta: ");
        var tipo = leitura.nextLine();
        var json = consumoAPI.obterDados(ENDERECO + tipo + "/marcas");
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
        System.out.println("Escolha agora o codigo do marca: ");
        var codigoMarca = leitura.nextLine();
        json = consumoAPI.obterDados(ENDERECO + tipo + "/marcas/" + codigoMarca + "/modelos");

        var modeloLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                        .collect(Collectors.toList());


        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o codigo do modelo: ");
        var codigoModelo = leitura.nextLine();
        json = consumoAPI.obterDados(ENDERECO + tipo + "/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos");
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            json = consumoAPI.obterDados(ENDERECO + tipo + "/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos/" + anos.get(i).codigo());
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("Todos os veiculos filtrados com avaliacoes por ano: ");
        veiculos.forEach(System.out::println);
    }
}
