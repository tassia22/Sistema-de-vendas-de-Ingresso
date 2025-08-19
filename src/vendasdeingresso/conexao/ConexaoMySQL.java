/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendasdeingresso.conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author tassi
 */
public class ConexaoMySQL {
    // Detalhes da conexão com o banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/vendas_ingressos";
    private static final String USUARIO = "root";
    private static final String SENHA = "y1v5t1w5";    

    
    public static Connection conectar() {
        Connection conexao = null;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            
          
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão com o MySQL estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC do MySQL não encontrado. Certifique-se de que o JAR está no classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados MySQL: " + e.getMessage());
            e.printStackTrace(); 
        }
        return conexao;
    }

    /**
     * @param conexao 
     */
    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão com o MySQL fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o MySQL: " + e.getMessage());
                e.printStackTrace(); 
            }
        }
    }

    public static void main(String[] args) {
        Connection testeConexao = ConexaoMySQL.conectar();
        if (testeConexao != null) {
            System.out.println("Teste de conexão bem-sucedido!");
            ConexaoMySQL.fecharConexao(testeConexao);
        } else {
            System.out.println("Teste de conexão falhou.");
        }
    }
}
