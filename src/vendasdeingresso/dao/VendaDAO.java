/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendasdeingresso.dao;

import vendasdeingresso.model.Venda; 
import vendasdeingresso.model.Usuario; 
import vendasdeingresso.model.Ingresso; 
import vendasdeingresso.model.Evento; 
import vendasdeingresso.conexao.ConexaoMySQL; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tassi
 */
public class VendaDAO {
    
    private UsuarioDAO usuarioDAO;
    private IngressoDAO ingressoDAO;
    private EventoDAO eventoDAO; 

    public VendaDAO() {
        this.usuarioDAO = new UsuarioDAO();
        this.ingressoDAO = new IngressoDAO();
        this.eventoDAO = new EventoDAO(); 
    }

    /**
     * @param venda 
     * @return 
     */
    public boolean inserirVenda(Venda venda) {
        String sql = "INSERT INTO vendas (data_venda, id_comprador, codigo_ingresso_vendido, quantidade, valor_total) VALUES (?, ?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return false;
            }

            conexao.setAutoCommit(false); 

            stmt = conexao.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(venda.getDataVenda()));
            stmt.setInt(2, venda.getComprador().getIdUsuario());
            stmt.setString(3, venda.getIngressoVendido().getCodigoUnico());
            stmt.setInt(4, venda.getQuantidade());
            stmt.setDouble(5, venda.getValorTotal());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                conexao.rollback();
                return false;
            }

            boolean ingressosAtualizados = eventoDAO.atualizarIngressosDisponiveis(
                venda.getIngressoVendido().getEventoAssociado().getIdEvento(),
                venda.getIngressoVendido().getEventoAssociado().getIngressosDisponiveis() - venda.getQuantidade() 
            );

            if (!ingressosAtualizados) { 
                conexao.rollback();
                System.err.println("Erro: Falha ao atualizar ingressos do evento. Venda desfeita.");
                return false;
            }

            conexao.commit(); 
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir venda: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conexao != null) conexao.rollback();
            } catch (SQLException ex) {
                System.err.println("Erro ao fazer rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.setAutoCommit(true); 
                ConexaoMySQL.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * @return 
     */
    public List<Venda> listarTodasVendas() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT numero_venda, data_venda, id_comprador, codigo_ingresso_vendido, quantidade, valor_total FROM vendas";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return vendas;
            }

            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int numeroVenda = rs.getInt("numero_venda");
                LocalDate dataVenda = rs.getDate("data_venda").toLocalDate();
                int idComprador = rs.getInt("id_comprador");
                String codigoIngressoVendido = rs.getString("codigo_ingresso_vendido");
                int quantidade = rs.getInt("quantidade");
                double valorTotal = rs.getDouble("valor_total");

                Usuario comprador = usuarioDAO.buscarUsuarioPorId(idComprador);
                Ingresso ingressoVendido = ingressoDAO.buscarIngressoPorCodigo(codigoIngressoVendido);

                if (comprador != null && ingressoVendido != null) {

                    Venda venda = new Venda(numeroVenda, dataVenda, comprador, ingressoVendido, quantidade);
                    vendas.add(venda);
                } else {
                    System.err.println("Aviso: Venda " + numeroVenda + " com comprador ou ingresso não encontrado. Venda ignorada.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                ConexaoMySQL.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return vendas;
    }
    
}
