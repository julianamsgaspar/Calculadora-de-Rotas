/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoedaa;

import java.util.List;

/**
 *A classe DijkstraAlgorithm implementa o algoritmo de Dijkstra para calcular o caminho mais curto entre cidades em um grafo 
 * Ela utiliza uma matriz de adjacência para representar o grafo e mantém informações sobre distâncias, cidades visitadas e predecessores
 * @author Juliana Gaspar
 */
public class DijkstraAlgorithm {

   // Atributos que armazenam informações do grafo e do algoritmo
    private int numCities;// Número de cidades no grafo
    private Matrix<Integer> graph;// Matriz de adjacência que representa as distâncias entre as cidades
    private int[] distances;// Array que armazena as menores distâncias calculadas
    private boolean[] visited; // Array que marca as cidades já visitadas
    private int[] predecessors;// Array que armazena os predecessores para reconstruir o caminho
     private List<City> cities;// Lista de cidades associadas ao grafo

     /**
     * Construtor que inicializa os atributos do algoritmo.
     * @param numCities Número total de cidades.
     * @param graph Matriz de adjacência que representa o grafo.
     * @param cities Lista de cidades.
     */
    public DijkstraAlgorithm(int numCities, Matrix<Integer> graph, List<City> cities) {
    this.numCities = numCities;
    this.graph = graph;
    this.cities = cities;  // Agora temos acesso à lista de cidades
    distances = new int[numCities];
    visited = new boolean[numCities];
    predecessors = new int[numCities];

    // Inicializar distâncias com valor "infinito"
    for (int i = 0; i < numCities; i++) {
        distances[i] = Integer.MAX_VALUE; // Representa infinito
        visited[i] = false;
        predecessors[i] = -1; // Nenhum predecessor no início
    }
}

   // Algoritmo de Dijkstra
    /**
     * Executa o algoritmo de Dijkstra a partir de uma cidade de origem.
     * @param source Índice da cidade de origem.
     * @throws Exception Se ocorrer um erro durante a execução.
     */
    public void dijkstra(int source) throws Exception{
    distances[source] = 0;

    for (int i = 0; i < numCities; i++) {
        // Encontre o vértice com a menor distância
        int u = minDistance();
        visited[u] = true;

        for (int v = 0; v < numCities; v++) {
    // Obtém o valor da aresta entre u e v
    Integer value = graph.get(u, v);
    
    // Verifica se o valor é nulo
    if (value == null) {
        // Valor ausente ou inválido, ignore esse vizinho
        continue; // Ou você pode tratar de outra forma, como lançar uma exceção
    }
    
    // Se o valor não for nulo, faça o cálculo normalmente
    if (!visited[v] && value != 0 && distances[u] != Integer.MAX_VALUE && distances[u] + value < distances[v]) {
        distances[v] = distances[u] + value;
        predecessors[v] = u;
    }
}

    }
}


    /**
     * Método auxiliar para encontrar a cidade com a menor distância ainda não visitada.
     * @return Índice da cidade com a menor distância.
     */
    private int minDistance() {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < numCities; i++) {
            if (!visited[i] && distances[i] <= min) {
                min = distances[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    /**
     * Retorna os detalhes do caminho mais curto até uma cidade de destino.
     * @param destination Índice da cidade de destino.
     * @return Detalhes do caminho mais curto e sua distância total.
     */
public String getShortestPathDetails(int destination) {
    if (distances[destination] == Integer.MAX_VALUE) {
        return "No route available";
    }

    // Montar a saída com StringBuilder para eficiência
    StringBuilder result = new StringBuilder();
    result.append("Shortest route to ")
          .append(cities.get(destination).getName())
          //.append(" starting from ") 
          //.append(cities.get(origin).getName())
          .append(":\n");

    // Adicionar o caminho
    result.append("Route: ");
    result.append(getPath(destination));

    // Adicionar o custo total
    result.append("\nTotal distance: ").append(distances[destination]).append(" km");

    return result.toString();
}

/**
     * Constrói o caminho mais curto até uma cidade de destino.
     * @param destination Índice da cidade de destino.
     * @return Caminho mais curto como uma string.
     */
private String getPath(int destination) {
    // Se o predecessor for -1, significa que chegamos à origem
    if (predecessors[destination] == -1) {
        return cities.get(destination).getName();
    }

    // Construir o caminho recursivamente
    return getPath(predecessors[destination]) + " => " + cities.get(destination).getName();
}


}
