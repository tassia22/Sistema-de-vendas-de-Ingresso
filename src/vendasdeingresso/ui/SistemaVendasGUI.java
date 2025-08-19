/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendasdeingresso.ui;

import vendasdeingresso.model.Evento; 
import vendasdeingresso.model.Ingresso; 
import vendasdeingresso.model.Usuario; 
import vendasdeingresso.model.Venda; 

import vendasdeingresso.dao.EventoDAO; 
import vendasdeingresso.dao.UsuarioDAO; 
import vendasdeingresso.dao.IngressoDAO; 
import vendasdeingresso.dao.VendaDAO; 
import vendasdeingresso.conexao.ConexaoMySQL;

import javax.swing.*;           
import java.awt.*;              
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.time.LocalDate;     
import java.util.List;

/**
 *
 * @author tassi
 */
public class SistemaVendasGUI  extends JFrame{
    
    private EventoDAO eventoDAO; 
    private UsuarioDAO usuarioDAO;
    private IngressoDAO ingressoDAO;
    private VendaDAO vendaDAO;

    private JTextArea outputArea; 
    private JButton btnCadastrarEvento;
    private JButton btnCadastrarUsuario;
    private JButton btnCadastrarIngresso;
    private JButton btnRealizarVenda;
    private JButton btnListarEventos;
    private JButton btnListarVendas;

    public SistemaVendasGUI() {
        super("Sistema de Vendas de Ingressos"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(800, 600); 
        setLocationRelativeTo(null); 

        this.eventoDAO = new EventoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.ingressoDAO = new IngressoDAO();
        this.vendaDAO = new VendaDAO();

        initComponents(); 
        if (ConexaoMySQL.conectar() != null) {
            outputArea.append("Conexão com o banco de dados estabelecida ao iniciar o sistema.\n\n");
            ConexaoMySQL.fecharConexao(ConexaoMySQL.conectar()); 
        } else {
            outputArea.append("ATENÇÃO: Não foi possível conectar ao banco de dados MySQL ao iniciar o sistema.\nVerifique as configurações e o servidor.\n\n");
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnCadastrarEvento = new JButton("Cadastrar Evento");
        btnCadastrarUsuario = new JButton("Cadastrar Usuário");
        btnCadastrarIngresso = new JButton("Cadastrar Ingresso");
        btnRealizarVenda = new JButton("Realizar Venda");
        btnListarEventos = new JButton("Listar Eventos");
        btnListarVendas = new JButton("Listar Vendas");

        panelBotoes.add(btnCadastrarEvento);
        panelBotoes.add(btnCadastrarUsuario);
        panelBotoes.add(btnCadastrarIngresso);
        panelBotoes.add(btnRealizarVenda);
        panelBotoes.add(btnListarEventos);
        panelBotoes.add(btnListarVendas);

        add(panelBotoes, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        btnCadastrarEvento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarEvento();
            }
        });

        btnCadastrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
        
        btnCadastrarIngresso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarIngresso();
            }
        });

        btnRealizarVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarVenda();
            }
        });

        btnListarEventos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarEventos();
            }
        });

        btnListarVendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarVendas();
            }
        });
    }

    private void cadastrarEvento() {
        String nome = JOptionPane.showInputDialog(this, "Nome do Evento:");
        if (nome == null || nome.trim().isEmpty()) return;

        String local = JOptionPane.showInputDialog(this, "Local:");
        if (local == null || local.trim().isEmpty()) return;

        String descricao = JOptionPane.showInputDialog(this, "Descrição:");
        if (descricao == null || descricao.trim().isEmpty()) return;

        String tipo = JOptionPane.showInputDialog(this, "Tipo (Música, Teatro, Esporte):");
        if (tipo == null || tipo.trim().isEmpty()) return;

        String dataStr = JOptionPane.showInputDialog(this, "Data (AAAA-MM-DD):");
        if (dataStr == null || dataStr.trim().isEmpty()) return;
        LocalDate dataEvento;
        try {
            dataEvento = LocalDate.parse(dataStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use AAAA-MM-DD.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ingressosStr = JOptionPane.showInputDialog(this, "Quantidade de Ingressos Disponíveis:");
        if (ingressosStr == null || ingressosStr.trim().isEmpty()) return;
        int ingressosDisp;
        try {
            ingressosDisp = Integer.parseInt(ingressosStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade de ingressos inválida. Digite um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Evento novoEvento = new Evento(nome, local, descricao, tipo, dataEvento, ingressosDisp);
        if (eventoDAO.inserirEvento(novoEvento)) { // Tenta inserir no banco
            outputArea.append("Evento '" + novoEvento.getNomeEvento() + "' cadastrado com sucesso no banco de dados!\n");
        } else {
            outputArea.append("Erro ao cadastrar evento '" + novoEvento.getNomeEvento() + "' no banco de dados.\n");
        }
        outputArea.append("\n"); 
    }

    private void cadastrarUsuario() {
        String nome = JOptionPane.showInputDialog(this, "Nome do Usuário:");
        if (nome == null || nome.trim().isEmpty()) return;

        String email = JOptionPane.showInputDialog(this, "Email do Usuário:");
        if (email == null || email.trim().isEmpty()) return;

        String tipo = JOptionPane.showInputDialog(this, "Tipo de Usuário (Cliente/Administrador):");
        if (tipo == null || tipo.trim().isEmpty()) return;

        String dataNascStr = JOptionPane.showInputDialog(this, "Data de Nascimento (AAAA-MM-DD):");
        if (dataNascStr == null || dataNascStr.trim().isEmpty()) return;
        LocalDate dataNascimento;
        try {
            dataNascimento = LocalDate.parse(dataNascStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use AAAA-MM-DD.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }
       
        Usuario novoUsuario = new Usuario(-1, nome, email, tipo, dataNascimento); 
        if (usuarioDAO.inserirUsuario(novoUsuario)) {
            outputArea.append("Usuário '" + novoUsuario.getNomeUsuario() + "' cadastrado com sucesso no banco de dados!\n");
        } else {
            outputArea.append("Erro ao cadastrar usuário '" + novoUsuario.getNomeUsuario() + "' no banco de dados.\n");
        }
        outputArea.append("\n");
    }
    
    private void cadastrarIngresso() {
        List<Evento> eventosDoBD = eventoDAO.listarTodosEventos();

        if (eventosDoBD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum evento cadastrado no banco de dados. Cadastre um evento primeiro.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String tipoIngresso = JOptionPane.showInputDialog(this, "Tipo do Ingresso (Pista, VIP, etc.):");
        if (tipoIngresso == null || tipoIngresso.trim().isEmpty()) return;

        String precoStr = JOptionPane.showInputDialog(this, "Preço do Ingresso:");
        if (precoStr == null || precoStr.trim().isEmpty()) return;
        double preco;
        try {
            preco = Double.parseDouble(precoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Digite um número.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String codigoUnico = JOptionPane.showInputDialog(this, "Código Único do Ingresso:");
        if (codigoUnico == null || codigoUnico.trim().isEmpty()) return;
        
        String[] nomesEventos = eventosDoBD.stream().map(Evento::getNomeEvento).toArray(String[]::new);
        String eventoEscolhidoNome = (String) JOptionPane.showInputDialog(this,
                "Selecione o Evento para este ingresso:",
                "Selecionar Evento",
                JOptionPane.QUESTION_MESSAGE,
                null, 
                nomesEventos,
                nomesEventos[0]);

        if (eventoEscolhidoNome == null) return; 
        
        Evento eventoAssociado = null;
        for (Evento e : eventosDoBD) { 
            if (e.getNomeEvento().equals(eventoEscolhidoNome)) {
                eventoAssociado = e;
                break;
            }
        }
        
        if (eventoAssociado == null) {
            JOptionPane.showMessageDialog(this, "Evento não encontrado na lista do banco de dados. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Ingresso novoIngresso = new Ingresso(tipoIngresso, preco, codigoUnico, eventoAssociado);
        if (ingressoDAO.inserirIngresso(novoIngresso)) { 
            outputArea.append("Ingresso '" + novoIngresso.getTipoIngresso() + "' para '" + eventoAssociado.getNomeEvento() + "' cadastrado com sucesso no banco de dados!\n");
        } else {
            outputArea.append("Erro ao cadastrar ingresso '" + novoIngresso.getCodigoUnico() + "' no banco de dados.\n");
        }
        outputArea.append("\n");
    }

    private void realizarVenda() {
        List<Usuario> usuariosDoBD = usuarioDAO.listarTodosUsuarios();
        List<Ingresso> ingressosDoBD = ingressoDAO.listarTodosIngressos();

        if (usuariosDoBD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum usuário cadastrado no banco de dados.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (ingressosDoBD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum ingresso disponível para venda no banco de dados.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] nomesUsuarios = usuariosDoBD.stream().map(Usuario::getNomeUsuario).toArray(String[]::new);
        String usuarioEscolhidoNome = (String) JOptionPane.showInputDialog(this,
                "Selecione o Comprador:",
                "Realizar Venda",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nomesUsuarios,
                nomesUsuarios[0]);

        if (usuarioEscolhidoNome == null) return;
        Usuario compradorSelecionado = null;
        for (Usuario u : usuariosDoBD) {
            if (u.getNomeUsuario().equals(usuarioEscolhidoNome)) {
                compradorSelecionado = u;
                break;
            }
        }
        if (compradorSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Comprador não encontrado na lista do banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] descIngressos = ingressosDoBD.stream()
                .map(i -> i.getTipoIngresso() + " - " + i.getEventoAssociado().getNomeEvento() + " (R$" + String.format("%.2f", i.getPreco()) + ")")
                .toArray(String[]::new);
        String ingressoEscolhidoDesc = (String) JOptionPane.showInputDialog(this,
                "Selecione o Ingresso:",
                "Realizar Venda",
                JOptionPane.QUESTION_MESSAGE,
                null,
                descIngressos,
                descIngressos[0]);

        if (ingressoEscolhidoDesc == null) return;
        Ingresso ingressoSelecionado = null;
        for (Ingresso i : ingressosDoBD) {
            String currentIngressoDesc = i.getTipoIngresso() + " - " + i.getEventoAssociado().getNomeEvento() + " (R$" + String.format("%.2f", i.getPreco()) + ")";
            if (currentIngressoDesc.equals(ingressoEscolhidoDesc)) {
                ingressoSelecionado = i;
                break;
            }
        }
        if (ingressoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Ingresso não encontrado na lista do banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String quantidadeStr = JOptionPane.showInputDialog(this, "Quantidade de Ingressos:");
        if (quantidadeStr == null || quantidadeStr.trim().isEmpty()) return;
        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida. Digite um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (quantidade <= 0) {
            JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Evento eventoAtualizado = eventoDAO.buscarEventoPorId(ingressoSelecionado.getEventoAssociado().getIdEvento());
        if (eventoAtualizado == null || eventoAtualizado.getIngressosDisponiveis() < quantidade) {
            JOptionPane.showMessageDialog(this, "Quantidade solicitada excede ingressos disponíveis para este evento (" + (eventoAtualizado != null ? eventoAtualizado.getIngressosDisponiveis() : "N/A") + ").", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Venda novaVenda = new Venda(-1, LocalDate.now(), compradorSelecionado, ingressoSelecionado, quantidade); 

        if (vendaDAO.inserirVenda(novaVenda)) { 
            outputArea.append("\n--- Venda Realizada com Sucesso no banco de dados! ---\n");
            outputArea.append("Detalhes da Venda:\n");
            outputArea.append("Comprador: " + novaVenda.getComprador().getNomeUsuario() + "\n");
            outputArea.append("Ingresso: " + novaVenda.getIngressoVendido().getTipoIngresso() + " de " + novaVenda.getIngressoVendido().getEventoAssociado().getNomeEvento() + "\n");
            outputArea.append("Quantidade: " + novaVenda.getQuantidade() + " - Valor Total: R$" + String.format("%.2f", novaVenda.getValorTotal()) + "\n");
            
            Evento eventoPosVenda = eventoDAO.buscarEventoPorId(ingressoSelecionado.getEventoAssociado().getIdEvento());
            if (eventoPosVenda != null) {
                outputArea.append("Ingressos restantes para o evento '" + eventoPosVenda.getNomeEvento() + "': " + eventoPosVenda.getIngressosDisponiveis() + "\n");
            }
        } else {
            outputArea.append("Erro ao realizar venda no banco de dados. Venda desfeita.\n");
        }
        outputArea.append("\n"); 
    }

    private void listarEventos() {
        outputArea.append("\n--- Eventos Cadastrados (do Banco de Dados) ---\n");
        List<Evento> eventosDoBD = eventoDAO.listarTodosEventos(); 

        if (eventosDoBD.isEmpty()) {
            outputArea.append("Nenhum evento cadastrado no banco de dados.\n\n");
        } else {
            for (Evento evento : eventosDoBD) {
                outputArea.append(evento.toString() + "\n");
            }
            outputArea.append("\n");
        }
    }

    private void listarVendas() {
        outputArea.append("\n--- Vendas Realizadas (do Banco de Dados) ---\n");
        List<Venda> vendasDoBD = vendaDAO.listarTodasVendas();

        if (vendasDoBD.isEmpty()) {
            outputArea.append("Nenhuma venda realizada no banco de dados.\n\n");
        } else {
            for (Venda venda : vendasDoBD) {
                outputArea.append(venda.toString() + "\n\n"); 
            }
            outputArea.append("\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SistemaVendasGUI().setVisible(true);
            }
        });
    }
    

}
