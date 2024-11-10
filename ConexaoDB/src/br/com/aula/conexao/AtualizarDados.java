package br.com.aula.conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

//Classe de Atualização de Dados
public class AtualizarDados {

	//Puxa a Classe ConexaoDB para realizar a conexão 
    public static void main(String[] args) {
        Connection conexao = ConexaoBD.conectar();
        
        //Caso a conexão esteja funcionando, irá acontecer um UPDATE no WAMP com as novas informações
        if (conexao != null) {
            String sql = "UPDATE alunos SET nome = ?, idade = ? WHERE id = ?";
            Scanner scanner = new Scanner(System.in);

            try {
                System.out.print("Digite o ID do aluno que deseja atualizar: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consumir quebra de linha

                System.out.print("Digite o novo nome do aluno: ");
                String nome = scanner.nextLine();

                System.out.print("Digite a nova idade do aluno: ");
                int idade = scanner.nextInt();

                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setInt(2, idade);
                stmt.setInt(3, id);

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Registro atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum registro encontrado com o ID especificado.");
                }
              
              //Catch para captar um erro e demonstrá-lo para o usuário
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar dados: " + e.getMessage());
            } finally {
                try {
                    if (conexao != null) {
                        conexao.close();
                    }
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
                scanner.close();
            }
        }
    }
}
