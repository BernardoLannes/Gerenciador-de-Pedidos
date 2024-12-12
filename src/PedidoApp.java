package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoApp {
    private static final String FILE_NAME = "pedidos.json";
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public static void main(String[] args) {
        loadPedidos();
        SwingUtilities.invokeLater(PedidoApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Gerenciador de Pedidos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton addPedidoButton = new JButton("Adicionar Pedido");
        JButton showClientButton = new JButton("Mostrar Clientes");
        JButton showPedidoButton = new JButton("Mostrar Pedidos");
        JButton searchByNumberButton = new JButton("Buscar por Pedido");
        JButton searchByNameButton = new JButton("Buscar por Cliente");
        JButton saveAndExitButton = new JButton("Salvar e Sair");

        panel.add(addPedidoButton);
        panel.add(showClientButton);
        panel.add(showPedidoButton);
        panel.add(searchByNumberButton);
        panel.add(searchByNameButton);
        panel.add(saveAndExitButton);

        // Configurando a tabela de pedidos
        String[] columnNames = { "Número", "Cliente", "Preço" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desabilitar edição das células
            }
        };

        JTable pedidoTable = new JTable(tableModel);
        pedidoTable.setFont(new Font("Arial", Font.PLAIN, 16));
        pedidoTable.setRowHeight(30);
        TableColumnModel columnModel = pedidoTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(100);

        JScrollPane tableScrollPane = new JScrollPane(pedidoTable);

        frame.add(panel, BorderLayout.WEST);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        addPedidoButton.addActionListener(e -> addPedido(tableModel));
        searchByNumberButton.addActionListener(e -> searchByNumber());
        searchByNameButton.addActionListener(e -> searchByName());
        saveAndExitButton.addActionListener(e -> saveAndExit(frame));
        showClientButton.addActionListener(e -> mostrarClientes());
        showPedidoButton.addActionListener(e -> mostrarPedidos());

        // Carregar pedidos na tabela ao iniciar
        loadPedidosToTable(tableModel);

        // Ação ao clicar em uma linha para edição
        pedidoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = pedidoTable.getSelectedRow();
                if (selectedRow != -1) {
                    Pedido pedido = pedidos.get(selectedRow);
                    editPedido(pedido, tableModel, selectedRow);
                }
            }
        });

        frame.setVisible(true);
    }

    private static void loadPedidosToTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Limpar a tabela
        for (Pedido pedido : pedidos) {
            tableModel.addRow(new Object[] { pedido.getNumero(), pedido.getCliente().getNome(), pedido.getPreco() });
        }
    }

    private static void addPedido(DefaultTableModel tableModel) {
        int numeroPedido = pedidos.stream()
                .mapToInt(Pedido::getNumero)
                .max()
                .orElse(0) + 1;

        JTextField precoField = new JTextField();
        JTextField nomeClienteField = new JTextField();
        JTextField telefoneClienteField = new JTextField();
        JTextField ruaField = new JTextField();
        JTextField bairroField = new JTextField();
        JTextField ufField = new JTextField();
        JTextField numeroEnderecoField = new JTextField();
        JTextField cepField = new JTextField();
        JTextField dataEntregaField = new JTextField();
        JCheckBox expressCheckBox = new JCheckBox("Entrega Express");

        // Desabilitar campo de data de entrega até o express ser marcado
        dataEntregaField.setEnabled(false);
        expressCheckBox.addActionListener(e -> dataEntregaField.setEnabled(expressCheckBox.isSelected()));

        JPanel panel = new JPanel(new GridLayout(12, 2));
        panel.add(new JLabel("Número do Pedido:"));
        panel.add(new JLabel(String.valueOf(numeroPedido)));
        panel.add(new JLabel("Preço:"));
        panel.add(precoField);
        panel.add(new JLabel("Nome do Cliente:"));
        panel.add(nomeClienteField);
        panel.add(new JLabel("Telefone do Cliente:"));
        panel.add(telefoneClienteField);
        panel.add(new JLabel("Rua:"));
        panel.add(ruaField);
        panel.add(new JLabel("Bairro:"));
        panel.add(bairroField);
        panel.add(new JLabel("UF:"));
        panel.add(ufField);
        panel.add(new JLabel("Número do Endereço:"));
        panel.add(numeroEnderecoField);
        panel.add(new JLabel("CEP:"));
        panel.add(cepField);
        panel.add(new JLabel("Entrega Express:"));
        panel.add(expressCheckBox);
        panel.add(new JLabel("Data de Entrega (se Express):"));
        panel.add(dataEntregaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Pedido", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                double preco = Double.parseDouble(precoField.getText());
                String nomeCliente = nomeClienteField.getText();
                String telefoneCliente = telefoneClienteField.getText();
                String rua = ruaField.getText();
                String bairro = bairroField.getText();
                String uf = ufField.getText();
                int numeroEndereco = Integer.parseInt(numeroEnderecoField.getText());
                int cep = Integer.parseInt(cepField.getText());

                Cliente cliente = new Cliente(nomeCliente, telefoneCliente,
                        new Endereco(rua, bairro, uf, numeroEndereco, cep));

                Pedido pedido;

                // Se entrega express estiver marcada
                if (expressCheckBox.isSelected()) {
                    if (dataEntregaField.getText().isEmpty()) {
                        throw new IllegalArgumentException("A data de entrega é obrigatória para entrega express!");
                    }
                    LocalDate dataEntrega = LocalDate.parse(dataEntregaField.getText());
                    pedido = new Pedido(numeroPedido, preco, cliente, dataEntrega); // Acrescenta 20% automaticamente
                } else {
                    pedido = new Pedido(numeroPedido, preco, cliente); // Normal
                }

                pedidos.add(pedido);
                tableModel.addRow(new Object[] { numeroPedido, cliente.getNome(), pedido.getPreco() });
                JOptionPane.showMessageDialog(null, "Pedido adicionado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: Dados inválidos! " + ex.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void editPedido(Pedido pedido, DefaultTableModel tableModel, int index) {
        JTextField precoField = new JTextField(String.valueOf(pedido.getPreco()));
        JTextField nomeClienteField = new JTextField(pedido.getCliente().getNome());
        JTextField telefoneClienteField = new JTextField(pedido.getCliente().getTelefone());
        JTextField ruaField = new JTextField(pedido.getCliente().getEndereco().getRua());
        JTextField bairroField = new JTextField(pedido.getCliente().getEndereco().getBairro());
        JTextField ufField = new JTextField(pedido.getCliente().getEndereco().getUf());
        JTextField numeroEnderecoField = new JTextField(String.valueOf(pedido.getCliente().getEndereco().getNumero()));
        JTextField cepField = new JTextField(String.valueOf(pedido.getCliente().getEndereco().getCep()));
        JTextField dataEntregaField = new JTextField(
                pedido.getDataEntrega() != null ? pedido.getDataEntrega().toString() : "");
        JCheckBox expressCheckBox = new JCheckBox("Entrega Express", pedido.getDataEntrega() != null);

        // Ativar/desativar o campo de data de entrega baseado no checkbox
        dataEntregaField.setEnabled(expressCheckBox.isSelected());
        expressCheckBox.addActionListener(e -> dataEntregaField.setEnabled(expressCheckBox.isSelected()));

        JPanel panel = new JPanel(new GridLayout(12, 2));
        panel.add(new JLabel("Número do Pedido:"));
        panel.add(new JLabel(String.valueOf(pedido.getNumero())));
        panel.add(new JLabel("Preço:"));
        panel.add(precoField);
        panel.add(new JLabel("Nome do Cliente:"));
        panel.add(nomeClienteField);
        panel.add(new JLabel("Telefone do Cliente:"));
        panel.add(telefoneClienteField);
        panel.add(new JLabel("Rua:"));
        panel.add(ruaField);
        panel.add(new JLabel("Bairro:"));
        panel.add(bairroField);
        panel.add(new JLabel("UF:"));
        panel.add(ufField);
        panel.add(new JLabel("Número do Endereço:"));
        panel.add(numeroEnderecoField);
        panel.add(new JLabel("CEP:"));
        panel.add(cepField);
        panel.add(new JLabel("Entrega Express:"));
        panel.add(expressCheckBox);
        panel.add(new JLabel("Data de Entrega (se Express):"));
        panel.add(dataEntregaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Editar Pedido", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Validação e parsing dos campos
                double preco = Double.parseDouble(precoField.getText());
                String nomeCliente = nomeClienteField.getText();
                String telefoneCliente = telefoneClienteField.getText();
                String rua = ruaField.getText();
                String bairro = bairroField.getText();
                String uf = ufField.getText();
                int numeroEndereco = Integer.parseInt(numeroEnderecoField.getText());
                int cep = Integer.parseInt(cepField.getText());

                // Validação de entrega express e data
                LocalDate dataEntrega = null;
                if (expressCheckBox.isSelected()) {
                    if (dataEntregaField.getText().isEmpty()) {
                        throw new IllegalArgumentException("A data de entrega é obrigatória para entrega express!");
                    }
                    dataEntrega = LocalDate.parse(dataEntregaField.getText());
                    preco *= 1.2; // Acrescentar taxa express
                }

                // Atualizar os dados do cliente e pedido
                Endereco endereco = new Endereco(rua, bairro, uf, numeroEndereco, cep);
                Cliente cliente = new Cliente(nomeCliente, telefoneCliente, endereco);
                pedido.setPreco(preco);
                pedido.setCliente(cliente);
                pedido.setDataEntrega(dataEntrega);

                // Atualizar a tabela
                tableModel.setValueAt(pedido.getNumero(), index, 0);
                tableModel.setValueAt(nomeCliente, index, 1);
                tableModel.setValueAt(preco, index, 2);

                JOptionPane.showMessageDialog(null, "Pedido atualizado com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erro: Formato inválido para valores numéricos.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void searchByNumber() {
        String numeroStr = JOptionPane.showInputDialog("Digite o número do pedido:");
        if (numeroStr != null) {
            try {
                int numero = Integer.parseInt(numeroStr);
                for (Pedido pedido : pedidos) {
                    if (pedido.getNumero() == numero) {
                        String detalhes = String.format(
                                "Detalhes do Pedido:\n\n" +
                                        "Número do Pedido: %d\n" +
                                        "Preço: R$ %.2f\n" +
                                        "Cliente: %s\n" +
                                        "Telefone: %s\n" +
                                        "Endereço: %s - %d\n\n",
                                pedido.getNumero(),
                                pedido.getPreco(),
                                pedido.getCliente().getNome(),
                                pedido.getCliente().getTelefone(),
                                pedido.getCliente().getEndereco().getRua(),
                                pedido.getCliente().getEndereco().getNumero());
                        JOptionPane.showMessageDialog(null, detalhes, "Pedido Encontrado",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Pedido não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Número inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void searchByName() {
        String nome = JOptionPane.showInputDialog("Digite o nome do cliente:");
        if (nome != null) {
            List<Pedido> pedidosDoCliente = new ArrayList<>();
            for (Pedido pedido : pedidos) {
                if (pedido.getCliente().getNome().equalsIgnoreCase(nome)) {
                    pedidosDoCliente.add(pedido);
                }
            }

            if (!pedidosDoCliente.isEmpty()) {
                StringBuilder detalhes = new StringBuilder();
                detalhes.append("Pedidos do Cliente ").append(nome).append("\n\n");
                for (Pedido pedido : pedidosDoCliente) {
                    detalhes.append(String.format(
                            "Número: %s | Preço: R$ %.2f\n",
                            (pedido.getNumero() > 9 ? Integer.toString(pedido.getNumero()) : "0" + pedido.getNumero()),
                            pedido.getPreco()));
                }
                JOptionPane.showMessageDialog(null, detalhes.toString(), "Pedidos Encontrados",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum pedido encontrado para o cliente.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void saveAndExit(JFrame frame) {
        savePedidos();
        JOptionPane.showMessageDialog(frame, "Dados salvos com sucesso!");
        frame.dispose();
    }

    private static void loadPedidos() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<List<Pedido>>() {
            }.getType();
            pedidos = gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado, criando nova lista.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void savePedidos() {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(pedidos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mostrarClientes() {
        if (pedidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente encontrado.", "Clientes",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder clientesInfo = new StringBuilder(
                "<html><body style='width: 500px;'>Clientes Cadastrados:<br><br>");
        List<String> clientesUnicos = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            Cliente cliente = pedido.getCliente();
            String clienteInfo = String.format(
                    "Nome: %s<br>Telefone: %s<br>Endereço: Rua %s, Bairro %s, UF %s, Nº %d, CEP: %d<br><br>",
                    cliente.getNome(),
                    cliente.getTelefone(),
                    cliente.getEndereco().getRua(),
                    cliente.getEndereco().getBairro(),
                    cliente.getEndereco().getUf(),
                    cliente.getEndereco().getNumero(),
                    cliente.getEndereco().getCep());
            if (!clientesUnicos.contains(cliente.getNome())) {
                clientesUnicos.add(cliente.getNome());
                clientesInfo.append(clienteInfo);
            }
        }
        clientesInfo.append("</body></html>");

        JLabel label = new JLabel(clientesInfo.toString());
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JOptionPane.showMessageDialog(null, scrollPane, "Clientes", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarPedidos() {
        if (pedidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum pedido encontrado.", "Pedidos",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder pedidosInfo = new StringBuilder("<html><body style='width: 500px;'>Pedidos Realizados:<br><br>");

        for (Pedido pedido : pedidos) {
            pedidosInfo.append(String.format(
                    "Número do Pedido: %d<br>" +
                            "Preço: R$ %.2f<br>" +
                            "Cliente: %s<br>" +
                            "Data do Pedido: %s<br>" +
                            "Data de Entrega: %s<br><br>",
                    pedido.getNumero(),
                    pedido.getPreco(),
                    pedido.getCliente().getNome(),
                    pedido.getDataPedido(),
                    pedido.getDataEntrega() != null ? pedido.getDataEntrega() : "Não definida"));
        }
        pedidosInfo.append("</body></html>");

        JLabel label = new JLabel(pedidosInfo.toString());
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JOptionPane.showMessageDialog(null, scrollPane, "Pedidos", JOptionPane.INFORMATION_MESSAGE);
    }
}
