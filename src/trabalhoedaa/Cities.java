/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoedaa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A classe 'Cities' gere as cidades e o grafo que as conecta, incluindo a
 * leitura de dados de um arquivo CSV a construção de um grafo de distâncias
 * entre cidades, e a serialização desses dados em um arquivo binário para
 * posterior uso.
 *
 * @author Juliana Gaspar
 */
public class Cities implements Serializable {
// Identificador para a serialização

    private static final long serialVersionUID = 1L;
    // Caminho do arquivo CSV
    private String filename = "worldcities.csv";
    // Caminho do arquivo binário
    private String filenameend = "worldcitiesend.obj";
// Distância auxiliar para definir conexão no grafo
    double auxDistance = 200;
    private Matrix<Integer> graph;// Matriz de adjacência representando o grafo
    private ArrayList<City> cities;// Lista de cidades

    /**
     * Construtor da classe Cities que inicializa a lista de cidades e tenta ler
     * dados do arquivo serializado. Se não encontrar, lê os dados do CSV.
     *
     * @throws Exception se houver erros ao inicializar as cidades.
     */
    public Cities() throws Exception {
        cities = new ArrayList<>();// Inicializa a lista de cidades
        File data = new File(                filenameend);// Lê do arquivo binário
    //(filenameend);// Arquivo a ser lido
        try {
            if (data.exists()) {
                readFromFile(filenameend);// Lê do arquivo binário
            } else {
                readFromCSV();// Lê do arquivo CSV
            }
        } catch (FileNotFoundException e) {// quando o arquivo não é encontrado
            throw new FileNotFoundException("File not found: " + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Error reaching file: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Unexpected error inicializing cities: " + e.getMessage());
        }
    }

    /**
     * Lê dados do ficheiro CSV e constrói o grafo com base nas cidades lidas.
     *
     * @throws Exception se houver erro ao processar o arquivo CSV.
     */
    private void readFromCSV() throws Exception {
        String name, country, aux; // Variáveis temporárias para dados da cidade
        double latitude, longitude;// Coordenadas geográficas

        try (Scanner s = new Scanner( new File("/CitiesData/worldcities.csv"), "UTF-8")) {      //new File(filename), "UTF-8")) {
            s.nextLine(); /// Ignora cabeçalho do ficheiro
            while (s.hasNext()) { // verifica se há mais linhas 
                String line = s.nextLine().trim(); //remove espasos adicionais
                if (line.isEmpty()) {
                    break;
                }

                try (Scanner l = new Scanner(line)) { //Analisa a linha individualmente
                    l.useDelimiter("\"");//Define o delimitador como aspas duplas (")

                    l.next();
                    l.next(); // Ignora dois primeiros campos
                    name = l.next();// Lê o nome da cidade
                    l.next();
                    aux = l.next();// Lê a latitude
                    latitude = Double.parseDouble(aux);
                    l.next();
                    aux = l.next();// Lê a longitude
                    longitude = Double.parseDouble(aux);
                    l.next();
                    country = l.next();// Lê o país

                    if (!country.equalsIgnoreCase("portugal") && !country.equalsIgnoreCase("spain") && !country.equalsIgnoreCase("france") && !country.equalsIgnoreCase("belgium") ) {
                        //&&!country.equalsIgnoreCase("france")&&!country.equalsIgnoreCase("belgium")&&!country.equalsIgnoreCase("germany")&&!country.equalsIgnoreCase("switzerland")
                        continue;// Filtra países indesejados
                    }
                    //Cria um objeto City com os dados lidos
                    City t = new City(name, latitude, longitude, country);
                    cities.add(t);// Adiciona à lista de cidades
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                }
            }
        }

        buildGraph(); // Chama para construir o grafo com as cidades 

        saveToFile(filenameend); // Salva os dados em arquivo serializado
    }

    /**
     * Método responsável por construir o grafo com base nas distâncias entre as
     * cidades. A matriz de adjacência é preenchida com os valores de distância
     * entre cada par de cidades.
     *
     * @throws Exception Caso ocorra algum erro ao definir o grafo ou calcular
     * distâncias
     */
    private void buildGraph() throws Exception {
        // Criação da matriz com o número de cidades (a matriz será de adjacência)
        graph = new Matrix<>(cities.size(), cities.size());

        // Iteração sobre todas as cidades para preencher as distâncias entre elas
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                // Obtém as cidades c1 e c2 da lista de cidades
                City c1 = cities.get(i);
                City c2 = cities.get(j);

                // Se as cidades não são iguais, calcula a distância entre elas
                if (!c1.equals(c2)) {
                    // Calcula a distância entre c1 e c2 (presumivelmente, método Distancia retorna a distância)
                    double dist = c1.Distancia(c2);

                    // Verifica se a distância é menor que o limite de distância auxiliar (auxDistance)
                    if (dist < auxDistance) {
                        // Define a distância entre c1 e c2 na matriz (grafo)
                        graph.set(i, j, (int) Math.round(dist)); // A distância é arredondada para um valor inteiro
                    }
                } else {
                    // Caso as cidades sejam a mesma, define a distância como 0

                    graph.set(i, j, 0); // A distância de uma cidade para si mesma é sempre 0
                }
            }
        }
    }

    /**
     * Metodo que altera a distancia em km para a nova autonomia do veiculo
     *
     * @param newDistance variavel com a nova distancia em km para a nova autonomia do veiculo 
     */
    public void setAuxDistance(double newDistance) throws Exception {
        if (newDistance <= 0) {
            throw new IllegalArgumentException("The autonomy must be greater than 0.");
        }
        if (newDistance > 1000) {
            throw new IllegalArgumentException("The autonomy must not exceed 1000 km.");
        }
        this.auxDistance = newDistance; // Atualiza a distância auxiliar
        buildGraph(); // Refaz o grafo com a nova autonomia
    }

    /**
     * Retorna a distancia de km que o carro tem a autonomia
     *
     * @return a distancia
     */
    public double getAuxDistance() {
        return auxDistance;
    }

    /**
     * Serializa os dados da classe Cities para um arquivo.
     *
     * @param filename Caminho do arquivo para salvar os dados.
     */
    private void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lê os dados da classe Cities de um ficheiro serializado.
     *
     * @param filename Caminho do arquivo para leitura.
     */
    private void readFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Cities c = (Cities) ois.readObject();
            this.cities = c.cities;
            this.graph = c.graph;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna o número total de cidades.
     *
     * @return Número de cidades.
     */
    public int getNumberCity() {
        return cities.size();
    }

    /**
     * Retorna uma cidade pelo índice.
     *
     * @param index Índice da cidade.
     * @return Objeto City correspondente.
     */
    public City getCity(int index) {
        if (index < 0 || index >= cities.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return cities.get(index);
    }

    /**
     * Retorna o grafo das cidades.
     *
     * @return Matriz de adjacência do grafo.
     */
    public Matrix<Integer> getGraph() {
        return graph;
    }

    /**
     * Retorna o índice de uma cidade pelo nome.
     *
     * @param city Nome da cidade.
     * @return Índice da cidade ou -1 se não encontrada.
     */
    public int getCity(String city) {
        for (int i = 0; i < cities.size(); i++) {
            if (city.trim().equalsIgnoreCase(cities.get(i).name.trim())) {
                return i;// Retorna o índice se encontrar a cidade

            }
        }
        return -1;// Retorna -1 se não encontrar
    }

    /**
     * Retorna a lista de cidades.
     *
     * @return Lista de objetos City.
     */
    public List<City> getCities() {
        return cities;  // Retorna a lista de cidades
    }

}
