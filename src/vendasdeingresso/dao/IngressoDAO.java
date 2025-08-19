/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendasdeingresso.dao;

import vendasdeingresso.model.Evento; 
import vendasdeingresso.model.Ingresso; 
import vendasdeingresso.conexao.ConexaoMySQL; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tassi
 */
public class IngressoDAO {
    
    private EventoDAO eventoDAO; 

    public IngressoDAO() {
        this.eventoDAO = new EventoDAO(); 
    }

    /** 
     * @param ingresso
     * @return 
     */
    public boolean inserirIngresso(Ingresso ingresso) {

        String sql = "INSERT INTO ingressos (codigo_unico, tipo_ingresso, preco, id_evento) VALUES (?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return false;
            }

            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, ingresso.getCodigoUnico());
            stmt.setString(2, ingresso.getTipoIngresso());
            stmt.setDouble(3, ingresso.getPreco());
            
            
            Evento eventoAssociadoDB = eventoDAO.buscarEventoPorNome(ingresso.getEventoAssociado().getNomeEvento());
            
            if (eventoAssociadoDB == null) {
                System.err.println("Erro: Evento associado ao ingresso não encontrado no banco de dados para o nome: " + ingresso.getEventoAssociado().getNomeEvento());
                return false;
            }
        
       
            stmt.setInt(4, eventoAssociadoDB.getIdEvento()); 

            // Executa a inserção
            int rowsAffected = stmt.executeUpdate(); 
            return rowsAffected > 0; 

        } catch (SQLException e) {
            System.err.println("Erro ao inserir ingresso: " + e.getMessage());
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

   
    public List<Ingresso> listarTodosIngressos() {
        List<Ingresso> ingressos = new ArrayList<>();
        String sql = "SELECT codigo_unico, tipo_ingresso, preco, id_evento FROM ingressos";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return ingressos; 
            }

            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery(); 

           
            while (rs.next()) {
                String codigoUnico = rs.getString("codigo_unico");
                String tipoIngresso = rs.getString("tipo_ingresso");
                double preco = rs.getDouble("preco");
                int idEvento = rs.getInt("id_evento");

                Evento eventoAssociado = eventoDAO.buscarEventoPorId(idEvento); 
                
                if (eventoAssociado != null) {
                    Ingresso ingresso = new Ingresso(tipoIngresso, preco, codigoUnico, eventoAssociado);
                    ingressos.add(ingresso);
                } else {
                    System.err.println("Aviso: Evento com ID " + idEvento + " não encontrado para o ingresso " + codigoUnico);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar ingressos: " + e.getMessage());
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
        return ingressos;
    }

    /**
     * @param codigoUnico 
     * @return 
     */
    public Ingresso buscarIngressoPorCodigo(String codigoUnico) {
        Ingresso ingresso = null;
        String sql = "SELECT codigo_unico, tipo_ingresso, preco, id_evento FROM ingressos WHERE codigo_unico = ?";
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
            stmt.setString(1, codigoUnico);
            rs = stmt.executeQuery();

            if (rs.next()) { 
                String tipoIngresso = rs.getString("tipo_ingresso");
                double preco = rs.getDouble("preco");
                int idEvento = rs.getInt("id_evento");

                Evento eventoAssociado = eventoDAO.buscarEventoPorId(idEvento); 
                
                if (eventoAssociado != null) {
                    ingresso = new Ingresso(tipoIngresso, preco, codigoUnico, eventoAssociado);
                } else {
                    System.err.println("Aviso: Evento com ID " + idEvento + " não encontrado para o ingresso " + codigoUnico);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ingresso por código: " + e.getMessage());
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
        return ingresso;
    }
    
    
}
