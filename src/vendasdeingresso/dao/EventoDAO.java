/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendasdeingresso.dao;


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
public class EventoDAO {
     /**
     * @param evento 
     * @return 
     */
    public boolean inserirEvento(Evento evento) {
        String sql = "INSERT INTO eventos (nome_evento, local, descricao, tipo, data_evento, ingressos_disponiveis) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return false;
            }

            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, evento.getNomeEvento());
            stmt.setString(2, evento.getLocal());
            stmt.setString(3, evento.getDescricao());
            stmt.setString(4, evento.getTipo());
            stmt.setDate(5, java.sql.Date.valueOf(evento.getDataEvento())); 
            stmt.setInt(6, evento.getIngressosDisponiveis());

            int rowsAffected = stmt.executeUpdate(); 
            return rowsAffected > 0; 

        } catch (SQLException e) {
            System.err.println("Erro ao inserir evento: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
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
    public List<Evento> listarTodosEventos() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT id_evento, nome_evento, local, descricao, tipo, data_evento, ingressos_disponiveis FROM eventos";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null; 

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return eventos; 
            }

            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery(); 

            while (rs.next()) {
                int idEvento = rs.getInt("id_evento"); 
                String nome = rs.getString("nome_evento");
                String local = rs.getString("local");
                String descricao = rs.getString("descricao");
                String tipo = rs.getString("tipo");
                LocalDate data = rs.getDate("data_evento").toLocalDate(); 
                int ingressosDisp = rs.getInt("ingressos_disponiveis");

                Evento evento = new Evento(idEvento, nome, local, descricao, tipo, data, ingressosDisp);
                eventos.add(evento); 
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos: " + e.getMessage());
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
        return eventos;
    }

    /**
     * Busca um evento pelo seu ID.
  
     * @param idEvento O ID do evento a ser buscado.
     * @return 
     */
    public Evento buscarEventoPorId(int idEvento) {
        Evento evento = null;
        String sql = "SELECT id_evento, nome_evento, local, descricao, tipo, data_evento, ingressos_disponiveis FROM eventos WHERE id_evento = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return null;
            }

            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idEvento);
            rs = stmt.executeQuery();

            if (rs.next()) { 
          
                int idDoBanco = rs.getInt("id_evento"); 
                String nome = rs.getString("nome_evento");
                String local = rs.getString("local");
                String descricao = rs.getString("descricao");
                String tipo = rs.getString("tipo");
                LocalDate data = rs.getDate("data_evento").toLocalDate();
                int ingressosDisp = rs.getInt("ingressos_disponiveis");

                evento = new Evento(idDoBanco, nome, local, descricao, tipo, data, ingressosDisp);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar evento por ID: " + e.getMessage());
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
        return evento;
    }

    /**
     
     * @param nomeEvento 
     * @return
     */
    public Evento buscarEventoPorNome(String nomeEvento) {
        Evento evento = null;
        String sql = "SELECT id_evento, nome_evento, local, descricao, tipo, data_evento, ingressos_disponiveis FROM eventos WHERE nome_evento = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return null;
            }

            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nomeEvento);
            rs = stmt.executeQuery();

            if (rs.next()) { 
                int idEvento = rs.getInt("id_evento"); 
                String nome = rs.getString("nome_evento");
                String local = rs.getString("local");
                String descricao = rs.getString("descricao");
                String tipo = rs.getString("tipo");
                LocalDate data = rs.getDate("data_evento").toLocalDate();
                int ingressosDisp = rs.getInt("ingressos_disponiveis");

                evento = new Evento(idEvento, nome, local, descricao, tipo, data, ingressosDisp);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar evento por nome: " + e.getMessage());
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
        return evento;
    }
    
    /**
    
     * @param idEvento 
     * @param novaQuantidade 
     * @return
     */
    public boolean atualizarIngressosDisponiveis(int idEvento, int novaQuantidade) {
        String sql = "UPDATE eventos SET ingressos_disponiveis = ? WHERE id_evento = ?";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return false;
            }

            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, idEvento);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar ingressos disponíveis para o evento: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                ConexaoMySQL.fecharConexao(conexao);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
