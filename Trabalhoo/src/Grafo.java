import java.util.ArrayList;
import java.util.List;

// Classe que representa a estrutura do Grafo usando Matriz de Adjacência
public class Grafo {
    private int[][] matrizAdjacencia;
    private List<String> vertices;
    private int maxVertices;
    private boolean ehDirecionado;

    // Construtor do Grafo
    public Grafo(int maxVertices, boolean ehDirecionado) {
        this.maxVertices = maxVertices;
        this.ehDirecionado = ehDirecionado;
        this.matrizAdjacencia = new int[maxVertices][maxVertices];
        this.vertices = new ArrayList<>();
    }

    // Método para adicionar um Vértice (Nó) no Grafo
    public boolean adicionarVertice(String nome) {
        if (vertices.contains(nome)) {
            return false; // Nó já existe
        }
        if (vertices.size() >= maxVertices) {
            throw new IllegalStateException("Capacidade máxima de vértices atingida!");




        }
        vertices.add(nome);
        return true;
    }

    // Método para adicionar uma Aresta (Conexão) entre dois nós
    public void adicionarAresta(String origem, String destino) {
        int indiceOrigem = vertices.indexOf(origem);
        int indiceDestino = vertices.indexOf(destino);

        // Tratamento de Exceção caso algum nó não exista
        if (indiceOrigem == -1 || indiceDestino == -1) {
            throw new IllegalArgumentException("Vértice de origem ou destino não encontrado no grafo.");
        }

        // Define a conexão na matriz (1 significa que há conexão)
        matrizAdjacencia[indiceOrigem][indiceDestino] = 1;

        // Se NÃO for direcionado, a conexão funciona nos dois sentidos
        if (!ehDirecionado) {
            matrizAdjacencia[indiceDestino][indiceOrigem] = 1;
        }
    }

    // Método auxiliar para verificar se um nó existe
    public boolean existeVertice(String nome) {
        return vertices.contains(nome);
    }

    // Getters necessários para os algoritmos de busca acessarem os dados
    public List<String> getVertices() {
        return vertices;
    }

    public int[][] getMatrizAdjacencia() {
        return matrizAdjacencia;
    }
}