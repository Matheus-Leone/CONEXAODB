
package br.com.aula.conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Classe de Leitura de Dados
public class LerDados {

	//Puxa a Classe ConexaoDB para realizar a conexão 
    public static void main(String[] args) {
        Connection conexao = ConexaoBD.conectar();

        //Caso a conexão esteja funcionando, irá acontecer um SELECT no WAMP pedindo as informações
        if (conexao != null) {
            String sql = "SELECT id, nome, idade FROM alunos";
            
            try {
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                System.out.println("Registros da tabela 'alunos':");
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    int idade = rs.getInt("idade");
                    System.out.println("ID: " + id + ", Nome: " + nome + ", Idade: " + idade);
                }

              //Catch para captar um erro e demonstrá-lo para o usuário
            } catch (SQLException e) {
                System.err.println("Erro ao ler dados: " + e.getMessage());
            } finally {
                try {
                    if (conexao != null) {
                        conexao.close();
                    }
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
}
