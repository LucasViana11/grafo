import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Interface Gráfica para gerenciar o Grafo e exibir as Buscas (BFS e DFS)
// Código desenvolvido com suporte de IA para fins acadêmicos (IFMA - 2026)
public class InterfaceGrafo extends JFrame {
    private Grafo grafo;

    // Componentes de configuração
    private JTextField txtMaxVertices;
    private JCheckBox chkDirecionado;
    private JButton btnCriarGrafo;

    // Componentes de manipulação do grafo
    private JTextField txtNomeVertice;
    private JButton btnAddVertice;
    private JTextField txtOrigemAresta, txtDestinoAresta;
    private JButton btnAddAresta;

    // Componentes de Busca
    private JTextField txtOrigemBusca, txtDestinoBusca;
    private JButton btnExecutarBusca;

    // Área de resultados
    private JTextArea txtResultado;

    public InterfaceGrafo() {
        super("IFMA - Buscas em Grafos (BFS e DFS)");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setLayout(new BorderLayout());

        // Painel Superior: Configuração Inicial do Grafo
        JPanel painelConfig = new JPanel(new FlowLayout());
        painelConfig.setBorder(BorderFactory.createTitledBorder("1. Inicializar Grafo"));

        painelConfig.add(new JLabel("Máx Vértices:"));
        txtMaxVertices = new JTextField("10", 4);
        painelConfig.add(txtMaxVertices);

        chkDirecionado = new JCheckBox("Direcionado");
        painelConfig.add(chkDirecionado);

        btnCriarGrafo = new JButton("Criar Novo Grafo");
        painelConfig.add(btnCriarGrafo);

        // Painel Central: Cadastro de Elementos e Buscas
        JPanel painelCentral = new JPanel(new GridLayout(3, 1, 5, 5));

        // Subpainel: Vértices
        JPanel painelVertices = new JPanel(new FlowLayout());
        painelVertices.setBorder(BorderFactory.createTitledBorder("2. Adicionar Vértices (Nós)"));
        painelVertices.add(new JLabel("Nome do Vértice:"));
        txtNomeVertice = new JTextField(8);
        painelVertices.add(txtNomeVertice);
        btnAddVertice = new JButton("Adicionar");
        painelVertices.add(btnAddVertice);
        painelCentral.add(painelVertices);

        // Subpainel: Arestas
        JPanel painelArestas = new JPanel(new FlowLayout());
        painelArestas.setBorder(BorderFactory.createTitledBorder("3. Adicionar Conexões (Arestas)"));
        painelArestas.add(new JLabel("Origem:"));
        txtOrigemAresta = new JTextField(5);
        painelArestas.add(txtOrigemAresta);
        painelArestas.add(new JLabel("Destino:"));
        txtDestinoAresta = new JTextField(5);
        painelArestas.add(txtDestinoAresta);
        btnAddAresta = new JButton("Conectar");
        painelArestas.add(btnAddAresta);
        painelCentral.add(painelArestas);

        // Subpainel: Execução de Buscas
        JPanel painelBusca = new JPanel(new FlowLayout());
        painelBusca.setBorder(BorderFactory.createTitledBorder("4. Executar Buscas"));
        painelBusca.add(new JLabel("Nó Origem (A):"));
        txtOrigemBusca = new JTextField(5);
        painelBusca.add(txtOrigemBusca);
        painelBusca.add(new JLabel("Nó Destino (B):"));
        txtDestinoBusca = new JTextField(5);
        painelBusca.add(txtDestinoBusca);
        btnExecutarBusca = new JButton("Buscar Caminhos");
        painelBusca.add(btnExecutarBusca);
        painelCentral.add(painelBusca);

        // Painel Inferior: Resultados
        JPanel painelResultado = new JPanel(new BorderLayout());
        painelResultado.setBorder(BorderFactory.createTitledBorder("Resultados das Buscas"));
        txtResultado = new JTextArea(8, 50);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtResultado);
        painelResultado.add(scroll, BorderLayout.CENTER);

        // Adicionando painéis principais à janela
        this.add(painelConfig, BorderLayout.NORTH);
        this.add(painelCentral, BorderLayout.CENTER);
        this.add(painelResultado, BorderLayout.SOUTH);

        //  Gerenciamento de Cliques (Listeners)

        // Ação: Criar Grafo
        btnCriarGrafo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int max = Integer.parseInt(txtMaxVertices.getText().trim());
                    boolean dir = chkDirecionado.isSelected();
                    grafo = new Grafo(max, dir);
                    txtResultado.setText("Grafo inicializado com sucesso!\n" +
                            "Modo: " + (dir ? "Direcionado" : "Não Direcionado") + " | Limite: " + max + " nós.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InterfaceGrafo.this, "Por favor, digite um número válido para o máximo de vértices.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação: Adicionar Vértice (Modificado para manter os nós sempre visíveis no topo do painel)
        btnAddVertice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verificarGrafoNulo()) return;
                String nome = txtNomeVertice.getText().trim();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(InterfaceGrafo.this, "O nome do vértice não pode ser vazio.", "Erro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    boolean adicionou = grafo.adicionarVertice(nome);
                    if (adicionou) {
                        // Limpa a tela e mostra a lista atualizada de forma bem clara no topo do painel de resultados
                        txtResultado.setText("=== GRAFO CONFIGURADO ===\n" +
                                "Vértices registrados no sistema: " + grafo.getVertices() + "\n" +
                                "=========================\n");
                        txtNomeVertice.setText("");
                    } else {
                        JOptionPane.showMessageDialog(InterfaceGrafo.this, "O vértice '" + nome + "' já existe.", "Erro", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(InterfaceGrafo.this, ex.getMessage(), "Erro de Limite", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação: Adicionar Aresta
        btnAddAresta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verificarGrafoNulo()) return;
                String orig = txtOrigemAresta.getText().trim();
                String dest = txtDestinoAresta.getText().trim();

                if (orig.isEmpty() || dest.isEmpty()) {
                    JOptionPane.showMessageDialog(InterfaceGrafo.this, "Informe a origem e o destino da conexão.", "Erro", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                try {
                    grafo.adicionarAresta(orig, dest);
                    txtResultado.append("\nAresta adicionada: " + orig + " <-> " + dest);
                    txtOrigemAresta.setText("");
                    txtDestinoAresta.setText("");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(InterfaceGrafo.this, ex.getMessage(), "Vértice Inválido", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação: Buscar Caminhos (BFS e DFS) - Versão Final com listagem de Vértices e Conexões
        btnExecutarBusca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verificarGrafoNulo()) return;
                String orig = txtOrigemBusca.getText().trim();
                String dest = txtDestinoBusca.getText().trim();

                if (orig.isEmpty() || dest.isEmpty()) {
                    JOptionPane.showMessageDialog(InterfaceGrafo.this, "Informe os nós A e B para busca.", "Erro", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!grafo.existeVertice(orig) || !grafo.existeVertice(dest)) {
                    JOptionPane.showMessageDialog(InterfaceGrafo.this, "Um ou ambos os nós selecionados não existem no grafo.", "Nó Inexistente", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 1. Executa os algoritmos de busca
                List<String> caminhoBFS = Buscador.buscaLargura(grafo, orig, dest);
                List<String> caminhoDFS = Buscador.buscaProfundidade(grafo, orig, dest);

                // 2. Monta o histórico visual do Grafo para o usuário não se perder
                StringBuilder sb = new StringBuilder();
                sb.append("==================================================\n");
                sb.append("ESTRUTURA ATUAL DO GRAFO\n");
                sb.append("==================================================\n");
                sb.append("Vértices Registrados: ").append(grafo.getVertices()).append("\n");
                sb.append("Conexões (Arestas) Ativas:\n");

                // Varre a matriz de adjacência para descobrir as conexões reais
                java.util.List<String> listaVertices = grafo.getVertices();
                int[][] matriz = grafo.getMatrizAdjacencia();
                boolean temAresta = false;

                for (int i = 0; i < listaVertices.size(); i++) {
                    // Se for não-direcionado, olhamos j=i para não duplicar a exibição da mesma linha
                    for (int j = 0; j < listaVertices.size(); j++) {
                        if (matriz[i][j] == 1) {
                            sb.append("  • ").append(listaVertices.get(i)).append(" -> ").append(listaVertices.get(j)).append("\n");
                            temAresta = true;
                        }
                    }

                }
                if (!temAresta) {
                    sb.append("  (Nenhuma conexão adicionada ainda)\n");
                }

                sb.append("==================================================\n\n");

                // 3. Formata os Resultados das Buscas
                sb.append("=== RESULTADO DA BUSCA DE '").append(orig).append("' ATÉ '").append(dest).append("' ===\n\n");

                sb.append("Busca em Largura (BFS):\n");
                if (caminhoBFS.isEmpty()) {
                    sb.append("-> Não existe caminho viável entre os nós selecionados.\n");
                } else {
                    sb.append("-> ").append(String.join(" para ", caminhoBFS)).append("\n");
                }

                sb.append("\n");

                sb.append("Busca em Profundidade (DFS):\n");
                if (caminhoDFS.isEmpty()) {
                    sb.append("-> Não existe caminho viável entre os nós selecionados.\n");
                } else {
                    sb.append("-> ").append(String.join(" para ", caminhoDFS)).append("\n");
                }

                txtResultado.setText(sb.toString());
            }
        });

        this.setLocationRelativeTo(null); // Centraliza a tela
    }

    private boolean verificarGrafoNulo() {
        if (grafo == null) {
            JOptionPane.showMessageDialog(this, "Você precisa inicializar o grafo no botão superior primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    // Método principal para rodar o projeto
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceGrafo().setVisible(true);
            }
        });
    }
}