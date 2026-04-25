/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhoedaa;

import java.io.Serializable;
import javax.lang.model.element.Element;

/**
 * A classe AVLTree é uma implementação de uma árvore binária de pesquisa
 * balanceada.
 *
 * @author Juliana Gaspar
 * @param <T>
 */
public class AVLTree<T extends Comparable<T>> implements Serializable {

    private static final long serialVersionUID = 1L; // Identificador único para serialização
    private Node root;

    /**
     *
     * Classe interna Node representa um nó da árvore AVL.
     */
    private class Node implements Serializable {

        T data;
        int height;
        Node left;
        Node right;

        @Override
        public String toString() {
            return data.toString();
        }
    }

    /**
     * Retorna a representação da árvore em ordem crescente como uma String.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        toString(root, sb);
        return sb.toString().trim();
    }

    /**
     * Método auxiliar recursivo para gerar a representação da árvore em ordem.
     *
     * @param actual Nó atual.
     * @param sb StringBuffer para construir a saída.
     */
    private void toString(Node actual, StringBuffer sb) {
        if (actual != null) {
            toString(actual.left, sb);
            sb.append(actual.data).append(" ");
            toString(actual.right, sb);
        }
    }

    /**
     * Mostra a estrutura da árvore no console.
     */
    public void mostrar() {
        mostrar(root, 0, 0);
    }

    /**
     * Método auxiliar recursivo para exibir a árvore.
     *
     * @param actual Nó atual.
     * @param nivel Nível atual na árvore.
     * @param aux Direção (-1 para esquerda, 1 para direita).
     */
    private void mostrar(Node actual, int nivel, int aux) {
        if (actual != null) {
            mostrar(actual.right, nivel + 1, 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print(" ");
            }
            if (aux > 0) {
                System.out.print("/");
            }
            if (aux < 0) {
                System.out.print("\\");
            }
            System.out.println(actual.data);
            mostrar(actual.left, nivel + 1, -1);
        }
    }

    /**
     * Busca um elemento na árvore.
     *
     * @param o Elemento a ser buscado.
     * @return Elemento encontrado ou null se não existir.
     */
    public T search(T o) {
        return search(root, o);
    }

    /**
     * Método auxiliar recursivo para buscar um elemento.
     *
     * @param actual Nó atual.
     * @param o Elemento a ser buscado.
     * @return Elemento encontrado ou null se não existir.
     */
    private T search(Node actual, T o) {
        if (actual == null) {
            return null; // Elemento não encontrado
        }

        // Se o elemento for encontrado
        if (o.compareTo(actual.data) == 0) {
            return actual.data;
        }

        // Se o elemento for menor, procurar na subárvore esquerda
        if (o.compareTo(actual.data) < 0) {
            return search(actual.left, o);
        }

        // Se o elemento for maior, procurar na subárvore direita
        return search(actual.right, o);
    }

    /**
     * Calcula o fator de balanceamento de um nó.
     *
     * @param nodo Nó para calcular o fator de balanceamento.
     * @return Fator de balanceamento.
     */
    public int factor(Node nodo) {
        return height(nodo.left) - height(nodo.right);
    }

    /**
     * Retorna a altura de um nó.
     *
     * @param n Nó para calcular a altura.
     * @return Altura do nó.
     */
    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    /**
     * Realiza uma rotação simples à direita.
     *
     * @param y Nó desbalanceado.
     * @return Novo nó raiz após a rotação.
     */
    public Node rightRotation(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    /**
     * Realiza uma rotação simples à esquerda.
     *
     * @param x Nó desbalanceado.
     * @return Novo nó raiz após a rotação.
     */
    public Node leftRotation(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    /**
     * Realiza uma rotação dupla à direita.
     *
     * @param z Nó desbalanceado.
     * @return Novo nó raiz após a rotação.
     */
    public Node doubleRightRotation(Node z) {
        z.left = leftRotation(z.left);
        return rightRotation(z);
    }

    /**
     * Realiza uma rotação dupla à esquerda.
     *
     * @param z Nó desbalanceado.
     * @return Novo nó raiz após a rotação.
     */
    public Node doubleLeftRotation(Node z) {
        z.right = rightRotation(z.right);
        return leftRotation(z);
    }

    /**
     * Insere um novo elemento na árvore.
     *
     * @param o Elemento a ser inserido.
     */
    public void add(T o) {
        root = add(root, o);
    }

    /**
     * Método auxiliar recursivo para inserir um elemento.
     *
     * @param actual Nó atual.
     * @param o Elemento a ser inserido.
     * @return Novo nó raiz.
     */
    private Node add(Node actual, T o) {
        if (actual == null) {
            Node node = new Node();
            node.data = o;
            node.height = 1;
            return node;
        }

        if (o.compareTo(actual.data) < 0) {
            actual.left = add(actual.left, o);
        } else if (o.compareTo(actual.data) > 0) {
            actual.right = add(actual.right, o);
        } else {
            return actual; // Duplicates not allowed
        }
        // Atualiza a altura do nó atual.
        actual.height = 1 + Math.max(height(actual.left), height(actual.right));
        return balance(actual);// Balanceia o nó atual, se necessário.
    }

    /**
     * Realiza o balanceamento do nó.
     *
     * @param nodo Nó a ser balanceado.
     * @return Novo nó balanceado.
     */
    private Node balance(Node nodo) {
        int balanceFactor = factor(nodo);
// Rotação à direita se o fator de balanceamento for maior que 1.
        if (balanceFactor > 1) {
            if (factor(nodo.left) >= 0) {
                return rightRotation(nodo);
            } else {
                return doubleRightRotation(nodo);
            }
        }
// Rotação à esquerda se o fator de balanceamento for menor que -1.
        if (balanceFactor < -1) {
            if (factor(nodo.right) <= 0) {
                return leftRotation(nodo);
            } else {
                return doubleLeftRotation(nodo);
            }
        }

        return nodo;
    }

    /**
     * Remove um elemento da árvore.
     *
     * @param o Elemento a ser removido.
     * @return True se o elemento foi removido, false caso contrário.
     */
    public boolean remove(T o) {
        if (!contains(o)) {
            return false;
        }
        root = remove(root, o);
        return true;
    }

    /**
     * Método auxiliar recursivo para remover um elemento.
     *
     * @param actual Nó atual.
     * @param o Elemento a ser removido.
     * @return Novo nó raiz.
     */
    private Node remove(Node actual, T o) {
        if (actual == null) {
            return actual;
        }

        if (o.compareTo(actual.data) < 0) {
            actual.left = remove(actual.left, o);
        } else if (o.compareTo(actual.data) > 0) {
            actual.right = remove(actual.right, o);
        } else { // Nó com um filho ou nenhum filho.
            if ((actual.left == null) || (actual.right == null)) {
                Node temp = (actual.left != null) ? actual.left : actual.right;

                if (temp == null) {
                    temp = actual;
                    actual = null;
                } else {
                    actual = temp;
                }
            } else { // Nó com dois filhos.
                Node temp = findMinNode(actual.right);
                actual.data = temp.data;
                actual.right = remove(actual.right, temp.data);
            }
        }

        if (actual == null) {
            return actual;
        }
// Atualiza a altura e balanceia o nó atual.
        actual.height = Math.max(height(actual.left), height(actual.right)) + 1;
        return balance(actual);
    }

    /**
     * Encontra o nó com o menor valor.
     *
     * @param actual Nó atual.
     * @return Nó com o menor valor.
     */
    private Node findMinNode(Node actual) {
        while (actual.left != null) {
            actual = actual.left;
        }
        return actual;
    }

    /**
     * Verifica se a árvore contém um elemento.
     *
     * @param o Elemento a ser buscado.
     * @return True se o elemento está na árvore, false caso contrário.
     */
    public boolean contains(T o) {
        return contains(root, o);
    }

    /**
     * Verifica se a árvore contém um elemento.
     *
     * @param o Elemento a ser buscado.
     * @return True se o elemento está na árvore, false caso contrário.
     */
    private boolean contains(Node actual, T o) {
        if (actual == null) {
            return false;
        }
        if (o.compareTo(actual.data) == 0) {
            return true;
        }
        return o.compareTo(actual.data) < 0 ? contains(actual.left, o) : contains(actual.right, o);
    }

    /**
     *
     * Encontrar o valor mínimo na árvore
     *
     * @return retorna o valor minimo na árvore.
     */
    public T findMin() {
        Node minNode = findMinNode(root);
        return minNode != null ? minNode.data : null;
    }

    /**
     *
     * Encontrar o valor máximo na árvore
     *
     * @return retorna o valor máximo na árvore
     */
    public T findMax() {
        Node maxNode = findMaxNode(root);
        return maxNode != null ? maxNode.data : null;
    }

    /**
     * Encontra o nó com o maior valor.
     *
     * @param actual Nó atual.
     * @return Nó com o maior valor.
     */
    private Node findMaxNode(Node actual) {
        while (actual.right != null) {
            actual = actual.right;
        }
        return actual;
    }

    /**
     * teste da classe criada
     *
     * @param args
     */
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();

        // Teste de inserção
        tree.add(10);
        tree.add(20);
        tree.add(30); // Gera rotação simples à esquerda
        tree.add(40);
        tree.add(50);
        tree.add(25); // Gera rotação dupla à direita
        tree.add(20);

        // Imprime a árvore em ordem
        System.out.println(tree);

        // Visualiza a árvore
        tree.mostrar();

        // Teste de busca
        System.out.println("Contem 20? " + tree.contains(20));
        System.out.println("Contem 60? " + tree.contains(60));

        // Teste de remoção
        tree.remove(20); // Remove nó intermediário
        System.out.println("Apos remover 20:");
        tree.mostrar();

        // Teste de valores mínimo e máximo
        System.out.println("Minimo: " + tree.findMin());
        System.out.println("Maximo: " + tree.findMax());
    }

}
