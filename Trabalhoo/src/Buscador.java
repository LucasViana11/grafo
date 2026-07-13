import java.util.*;

// Classe responsável por executar os algoritmos de busca no Grafo
public class Buscador {

    // 1. BUSCA EM LARGURA (BFS) - Utiliza uma Fila (Queue)
    public static List<String> buscaLargura(Grafo grafo, String origem, String destino) {
        List<String> vertices = grafo.getVertices();
        int[][] matriz = grafo.getMatrizAdjacencia();

        int idxOrigem = vertices.indexOf(origem);
        int idxDestino = vertices.indexOf(destino);

        // Se os nós não existirem, retorna lista vazia (será tratado na interface)
        if (idxOrigem == -1 || idxDestino == -1) return new ArrayList<>();

        Queue<Integer> fila = new LinkedList<>();
        boolean[] visitados = new boolean[vertices.size()];

        // Array para reconstruir o caminho depois (guarda o pai de cada nó)
        int[] pai = new int[vertices.size()];
        Arrays.fill(pai, -1);

        // Inicia a busca pela origem
        fila.add(idxOrigem);
        visitados[idxOrigem] = true;

        boolean encontrou = false;

        while (!fila.isEmpty()) {

            int atual = fila.poll();

            if (atual == idxDestino) {
                encontrou = true;
                break;
            }

            // Olha os vizinhos na matriz de adjacência
            for (int i = 0; i < vertices.size(); i++) {
                if (matriz[atual][i] == 1 && !visitados[i]) {
                    visitados[i] = true;
                    pai[i] = atual; // Guarda de onde veio para reconstruir o caminho
                    fila.add(i);
                }
            }
        }

        if (!encontrou) return new ArrayList<>(); // Retorna vazio se não houver caminho
        return reconstruirCaminho(vertices, pai, idxDestino);
    }

    // 2. BUSCA EM PROFUNDIDADE (DFS) - Utiliza Recursão (Pilha do Sistema)
    public static List<String> buscaProfundidade(Grafo grafo, String origem, String destino) {
        List<String> vertices = grafo.getVertices();
        int[][] matriz = grafo.getMatrizAdjacencia();

        int idxOrigem = vertices.indexOf(origem);
        int idxDestino = vertices.indexOf(destino);

        if (idxOrigem == -1 || idxDestino == -1) return new ArrayList<>();

        boolean[] visitados = new boolean[vertices.size()];
        List<Integer> caminhoIndices = new ArrayList<>();

        // Dispara o método recursivo
        if (dfsRecursiva(idxOrigem, idxDestino, matriz, visitados, caminhoIndices)) {
            List<String> caminhoFinal = new ArrayList<>();
            for (int idx : caminhoIndices) {
                caminhoFinal.add(vertices.get(idx));
            }
            return caminhoFinal;
        }

        return new ArrayList<>(); // Caminho não encontrado
    }

    // Método auxiliar estritamente recursivo para a DFS
    private static boolean dfsRecursiva(int atual, int destino, int[][] matriz, boolean[] visitados, List<Integer> caminho) {
        visitados[atual] = true;
        caminho.add(atual);

        if (atual == destino) {
            return true;
        }

        for (int i = 0; i < matriz.length; i++) {
            if (matriz[atual][i] == 1 && !visitados[i]) {
                if (dfsRecursiva(i, destino, matriz, visitados, caminho)) {
                    return true;
                }
            }
        }

        // Backtracking: remove o nó se ele não levar ao destino
        caminho.remove(caminho.size() - 1);
        return false;
    }

    // Método auxiliar para montar a lista do caminho da BFS de trás para frente
    private static List<String> reconstruirCaminho(List<String> vertices, int[] pai, int destino) {
        LinkedList<String> caminho = new LinkedList<>();
        int atual = destino;
        while (atual != -1) {
            caminho.addFirst(vertices.get(atual));
            atual = pai[atual];
        }
        return caminho;
    }
}