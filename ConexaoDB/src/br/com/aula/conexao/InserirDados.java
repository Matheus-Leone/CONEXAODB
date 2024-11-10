package br.com.aula.conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Clase de Inserção de Dados
public class InserirDados {
	
	//Utilizar a Classe ConexaoDB para realizar Conexão com o WAMP
    public static void main(String[] args) {
        Connection conexao = ConexaoBD.conectar();
        
        //Caso a database estiver funcionando, irá acontecer um INSERT INTO no WAMP
        if (conexao != null) {
            String sql = "INSERT INTO alunos (nome, idade) VALUES (?, ?)";
            try {
                PreparedStatement stmt = conexao.prepareStatement(sql);

                // Inserindo primeiro registro
                stmt.setString(1, "João Silva");
                stmt.setInt(2, 20);
                stmt.executeUpdate();

                // Inserindo segundo registro
                stmt.setString(1, "Maria Souza");
                stmt.setInt(2, 22);
                stmt.executeUpdate();

                // Inserindo terceiro registro
                stmt.setString(1, "Pedro Santos");
                stmt.setInt(2, 25);
                stmt.executeUpdate();

                System.out.println("Dados inseridos com sucesso!");

                //Catch para captar um erro e demonstrá-lo para o usuário
            } catch (SQLException e) {
                System.err.println("Erro ao inserir dados: " + e.getMessage());
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

