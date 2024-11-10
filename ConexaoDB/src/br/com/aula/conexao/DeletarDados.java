package br.com.aula.conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

//Clase de Deletar Dados
public class DeletarDados {

	//Puxa a Classe ConexaoDB para realizar a conexão 
    public static void main(String[] args) {
        Connection conexao = ConexaoBD.conectar();
        
        //Caso a conexão esteja funcionando, irá acontecer um DELETE no WAMP das informações
        if (conexao != null) {
            String sql = "DELETE FROM alunos WHERE id = ?";
            Scanner scanner = new Scanner(System.in);

            try {
                System.out.print("Digite o ID do aluno que deseja deletar: ");
                int id = scanner.nextInt();

                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();

                //Caso Alguma informação tenha sido deletada, demonstra essa frase, se não, mostra que não foi possível realizar
                if (rowsDeleted > 0) {
                    System.out.println("Registro deletado com sucesso!");
                } else {
                    System.out.println("Nenhum registro encontrado com o ID especificado.");
                }

              //Catch para captar um erro e demonstrá-lo para o usuário
            } catch (SQLException e) {
                System.err.println("Erro ao deletar dados: " + e.getMessage());
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
