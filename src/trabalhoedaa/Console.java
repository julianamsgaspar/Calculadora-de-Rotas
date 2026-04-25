/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoedaa;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *Classe main onde é feito diretamente da consola, para teste
 * @author macie
 */
public class Console {

    public static void main(String[] args) {
        try {
            // Criação do objeto Cities, que carrega as informações sobre as cidades
            Cities c = new Cities();

            // Definição das cidades de origem e destino
            String sourceCity = "porto";
            String destinationCity = "lisbon";

            // Obtendo os índices das cidades de origem e destino
            int source = c.getCity(sourceCity);
            int destination = c.getCity(destinationCity);

            // Verificando se as cidades foram encontradas
            if (source == -1 || destination == -1) {
                System.out.println("Error: One of the cities not found");
                System.out.println("Origin city: " + sourceCity);
                System.out.println("Destination city: " + destinationCity);
                return;
            }

            // Imprimindo informações das cidades de origem e destino
            System.out.println("Origin city: " + c.getCities().get(source));
            System.out.println("Destination city: " + c.getCities().get(destination));

            // Imprimindo o número total de cidades carregadas
            System.out.println("Total cities in graph: " + c.getNumberCity());

            // Inicialização do algoritmo de Dijkstra
            DijkstraAlgorithm d = new DijkstraAlgorithm(c.getNumberCity(), c.getGraph(), c.getCities());

            // Execução do algoritmo de Dijkstra para encontrar os caminhos mais curtos
            d.dijkstra(source);

            // Obtendo os detalhes do caminho mais curto
            String shortestPathDetails = d.getShortestPathDetails(destination);

            // Exibindo o caminho mais curto no console
            System.out.println(shortestPathDetails);

        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading or loading the file " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            e.printStackTrace(); // Exibe a pilha de erros para depuração
        }
    }
}

