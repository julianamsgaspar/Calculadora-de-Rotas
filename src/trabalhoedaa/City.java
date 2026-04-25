/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoedaa;

import java.io.Serializable;
import java.util.Objects;
import java.lang.Math;

/**
 */
/* @author Juliana Gaspar
 */
// A classe 'City' representa uma cidade, com informações como nome, latitude, longitude e país.
// Ela implementa a interface Serializable, o que permite que objetos dessa classe sejam serializados (transformados em byte stream)
// e posteriormente gravados em um arquivo ou transmitidos em uma rede.
public class City implements Serializable {

    private static final long serialVersionUID = -8072140744201805275L;


    // Atributos da classe 'City' que armazenam as informações sobre a cidade.
    public String name;
    public double latitude;
    public double longitude;
    public String country;

    /**
     * Construtor que inicializa os atributos com os valores fornecidos.
     * @param name Nome da cidade.
     * @param latitude Latitude da cidade.
     * @param longitude Longitude da cidade.
     * @param country País da cidade.
     */
    public City(String name, double latitude, double longitude, String country) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;

    }

/**
     * Construtor padrão que inicializa os atributos com valores padrão.
     */
    public City() {
        this.name = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.country = "";
    }
    // Métodos getter e setter para acessar e modificar os atributos da cidade.
    /**
     * Retorna o nome da cidade.
     * @return Nome da cidade.
     */
    public String getName() {
        return name;
    }
/**
     * Define o nome da cidade.
     * @param name Novo nome da cidade.
     */
    public void setName(String name) {
        this.name = name;
    }
/**
     * Retorna a latitude da cidade.
     * @return Latitude da cidade.
     */
    public double getLatitude() {
        return latitude;
    }
/**
     * Define a latitude da cidade, garantindo que esteja no intervalo correto.
     * @param latitude Nova latitude da cidade.
     * @throws IllegalArgumentException Se a latitude não estiver entre -90 e 90.
     */
    public void setLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        }
        this.latitude = latitude;
    }
/**
     * Retorna a longitude da cidade.
     * @return Longitude da cidade.
     */
    public double getLongitude() {
        return longitude;
    }
/**
     * Define a longitude da cidade, garantindo que esteja no intervalo correto.
     * @param longitude Nova longitude da cidade.
     * @throws IllegalArgumentException Se a longitude não estiver entre -180 e 180.
     */
    public void setLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180.");
        }
        this.longitude = longitude;
    }
/**
     * Retorna o país da cidade.
     * @return País da cidade.
     */
    public String getCountry() {
        return country;
    }
/**
     * Define o país da cidade.
     * @param country Novo país da cidade.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Retorna uma representação textual do objeto.
     * @return Representação textual da cidade como String.
     * Esse método é útil para imprimir os detalhes da cidade.
     */
    @Override
    public String toString() {
        return String.format("City{name='%s', latitude=%.6f, longitude=%.6f, country='%s'}",
                name, latitude, longitude, country);
    }

    // Método hashCode() que gera um código hash para o objeto da classe 'City'.
    // Este método é utilizado em coleções que exigem um código hash, como HashMap ou HashSet.
    /**
     * Gera um código hash para o objeto.
     * @return Código hash da cidade.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude, country);
    }
    

    // Método equals() que compara dois objetos da classe 'City'.
    // Retorna true se os dois objetos forem iguais (mesmo nome, latitude, longitude e país).
    /**
     * Compara dois objetos City.
     * @param obj Objeto a ser comparado.
     * @return true se os objetos forem iguais; caso contrário, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        City other = (City) obj;
        return Double.compare(latitude, other.latitude) == 0
                && Double.compare(longitude, other.longitude) == 0
                && Objects.equals(name, other.name)
                && Objects.equals(country, other.country);
    }

    // Método 'Distancia()' que calcula a distância entre duas cidades, dada suas latitudes e longitudes.
    // A fórmula utilizada é a fórmula de Haversine, que calcula a distância em linha reta (circular) entre dois pontos na Terra.
    /**
     * Calcula a distância entre duas cidades usando a fórmula de Haversine.
     * @param other Outra cidade para calcular a distância.
     * @return Distância entre as cidades em quilômetros.
     */
    public double Distancia(City other) {
        final int EARTH_RADIUS = 6371; // Raio da Terra em quilômetros

        // Converter latitude e longitude de graus para radianos
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(other.latitude);
        double dLat = Math.toRadians(other.latitude - this.latitude);
        double dLon = Math.toRadians(other.longitude - this.longitude);

        // Fórmula de Haversine
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
