/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendasdeingresso.dao;

import vendasdeingresso.model.Usuario; 
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
public class UsuarioDAO {
   
   public boolean inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome_usuario, email, tipo_usuario, data_nascimento) VALUES (?, ?, ?, ?)";
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return false;
            }

            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTipoUsuario());
            stmt.setDate(4, java.sql.Date.valueOf(usuario.getDataNascimento())); // Converte LocalDate para java.sql.Date

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
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
     * Retorna uma lista de todos os usuários no banco de dados.
     *
     * @return Uma lista de objetos Usuario. Retorna uma lista vazia se nenhum usuário for encontrado.
     */
    public List<Usuario> listarTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nome_usuario, email, tipo_usuario, data_nascimento FROM usuarios";
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoMySQL.conectar();
            if (conexao == null) {
                System.err.println("Erro: Não foi possível obter conexão com o banco de dados.");
                return usuarios;
            }

            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nome = rs.getString("nome_usuario");
                String email = rs.getString("email");
                String tipo = rs.getString("tipo_usuario");
                LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();

                Usuario usuario = new Usuario(idUsuario, nome, email, tipo, dataNascimento);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
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
        return usuarios;
    }
    
    /**
     * Busca um usuário pelo seu ID.
     *
     * @param idUsuario O ID do usuário a ser buscado.
     * @return O objeto Usuario encontrado, ou null se não for encontrado.
     */
    public Usuario buscarUsuarioPorId(int idUsuario) {
        Usuario usuario = null;
        String sql = "SELECT id_usuario, nome_usuario, email, tipo_usuario, data_nascimento FROM usuarios WHERE id_usuario = ?";
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
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) { // Se encontrou um resultado
                String nome = rs.getString("nome_usuario");
                String email = rs.getString("email");
                String tipo = rs.getString("tipo_usuario");
                LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                usuario = new Usuario(idUsuario, nome, email, tipo, dataNascimento);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
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
        return usuario;
    }
}
