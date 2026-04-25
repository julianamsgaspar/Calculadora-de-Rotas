/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoedaa;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe Matrix representa uma matriz genérica, onde os elementos são
 * armazenados em uma árvore AVL.
 *
 * @param <T> Tipo genérico, permitindo armazenar qualquer tipo de dado na
 * matriz.
 */
public class Matrix<T> implements Serializable {

    // Serial Version UID para garantir a compatibilidade durante a serialização
    private static final long serialVersionUID = 1L;

    // A árvore AVL que armazena os elementos da matriz
    private AVLTree<Element<T>> data;

    // Número de linhas e colunas da matriz
    private int lines, columns;

    /**
     * Construtor padrão, inicializa uma árvore AVL vazia e define as dimensões
     * da matriz como 0
     *
     */
    public Matrix() {
        data = new AVLTree<>(); // Cria uma nova árvore AVL para armazenar os elementos da matriz
        this.lines = 0;
        this.columns = 0;
    }

    /**
     * Construtor que inicializa a matriz com um número específico de linhas e
     * colunas
     *
     * @param lines
     * @param columns
     */
    public Matrix(int lines, int columns) {
        data = new AVLTree<>(); // Cria a árvore AVL
        this.lines = lines;
        this.columns = columns;
    }

    /**
     * Método para obter o número de linhas da matriz
     *
     * @return retorna as linhas
     */
    public int getLines() {
        return lines;
    }

    /**
     * Método para obter o número de colunas da matriz
     *
     * @return retorna as colunas
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Método para definir um valor em uma posição específica da matriz. Se a
     * posição já tiver um valor, o valor será substituído.
     *
     * @param line Linha onde o valor será armazenado
     * @param column Coluna onde o valor será armazenado
     * @param value Valor a ser armazenado na matriz
     * @throws Exception Se as coordenadas fornecidas forem inválidas
     */
    public void set(int line, int column, T value) throws Exception {
        // Verifica se a linha e a coluna estão dentro dos limites da matriz
        if (line < 0 || line >= lines) {
            throw new Exception("Cannot add at line " + line + " in matrix with " + lines + " lines");
        }
        if (column < 0 || column >= columns) {
            throw new Exception("Cannot add at column " + column + " in matrix with " + columns + " columns");
        }

        // Cria um novo elemento com as coordenadas (linha, coluna) e o valor fornecido
        Element<T> newElement = new Element<>(line, column, value);

        // Tenta adicionar o novo elemento à árvore AVL
        if (!data.contains(newElement)) {
            data.add(newElement); // Se o elemento não existe, adiciona à árvore
        } else {
            // Se o elemento já existir, remove o antigo e adiciona o novo
            data.remove(newElement);
            data.add(newElement);
        }
    }

    /**
     * Método para obter o valor de um elemento em uma posição específica (linha
     * e coluna).
     *
     * @param line Linha onde o valor será recuperado
     * @param column Coluna onde o valor será recuperado
     * @return O valor armazenado na posição especificada ou null se não
     * encontrado
     * @throws Exception Se as coordenadas fornecidas forem inválidas
     */
    public T get(int line, int column) throws Exception {
        // Verifica se a linha e a coluna estão dentro dos limites da matriz
        if (line < 0 || line >= lines) {
            throw new Exception("Invalid line " + line + " for matrix with " + lines + " lines");
        }
        if (column < 0 || column >= columns) {
            throw new Exception("Invalid column " + column + " for matrix with " + columns + " columns");
        }

        // Cria um elemento de busca para procurar a posição (linha, coluna) desejada
        Element<T> searchElement = new Element<>(line, column, null);

        // Retorna o valor do elemento encontrado, ou null se não encontrado
        Element<T> result = data.search(searchElement);
        return result != null ? result.value : null;
    }

    /**
     * Método para gerar uma representação em string da matriz.
     *
     * @return Uma string que representa os dados da matriz
     */
    @Override
    public String toString() {
        return "Matrix{" + "data=" + data + '}'; // Retorna os dados da árvore AVL
    }

    /**
     * Classe interna 'Element' representa um elemento da matriz, com suas
     * coordenadas (linha e coluna) e valor.
     */
    private class Element<T> implements Comparable<Element<T>>, Serializable {

        int line, column; // Coordenadas do elemento (linha e coluna)
        T value;          // Valor armazenado no elemento

        // Construtor da classe Element, inicializa as coordenadas e o valor
        public Element(int line, int column, T value) {
            this.line = line;
            this.column = column;
            this.value = value;
        }

        /**
         * Método para gerar um hash único para o elemento baseado nas suas
         * coordenadas
         *
         *
         * @return retorna ombina a linha e a coluna para gerar um código hash
         * único
         */
        private String getHash() {
            return line + ":" + column; // Combina a linha e a coluna para gerar um código hash único
        }

        /**
         * Método para obter o valor do elemento
         *
         * @return o valor de t
         */
        public T getValue() {
            return value;
        }

        /**
         * Método para definir o valor do elemento
         */
        public void setValue(T value) {
            this.value = value;
        }

        /**
         * Sobrescreve o método hashCode() para garantir que a comparação de
         * elementos seja baseada em suas coordenadas
         */
        @Override
        public int hashCode() {
            return Objects.hash(line, column); // HashCode baseado nas coordenadas
        }

        /**
         * Método que compara dois elementos baseado no seu código hash (linha e
         * coluna)
         */
        @Override
        public int compareTo(Element<T> other) {
            return this.getHash().compareTo(other.getHash()); // Comparação dos elementos
        }

        /**
         * Método toString() para exibir as informações do elemento de forma
         * legível
         */
        @Override
        public String toString() {
            return "Element{" + "line=" + line + ", column=" + column + ", value=" + value + '}';
        }

        /**
         * Sobrescreve o método equals() para comparar dois elementos com base
         * nas suas coordenadas
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Element<?> other = (Element<?>) obj;
            return this.line == other.line && this.column == other.column; // Compara as coordenadas
        }
    }
}
