package br.com.aula.conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//Interface conectada ao WAMP
public class DatabaseCLI {

	//Criação de Scanner 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        //Layout das Opções
        while (running) {
            System.out.println("\n=== MENU DE GERENCIAMENTO DE DADOS ===");
            System.out.println("1. Inserir Aluno");
            System.out.println("2. Ler Alunos");
            System.out.println("3. Atualizar Aluno");
            System.out.println("4. Deletar Aluno");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir quebra de linha
            
            switch (opcao) {
                case 1 -> inserirAluno(scanner);
                case 2 -> lerAlunos();
                case 3 -> atualizarAluno(scanner);
                case 4 -> deletarAluno(scanner);
                case 5 -> {
                    running = false;
                    System.out.println("Saindo...");
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    // Método para inserir um aluno no banco de dados
    private static void inserirAluno(Scanner scanner) {
        Connection conexao = ConexaoBD.conectar();
        if (conexao != null) {
            String sql = "INSERT INTO alunos (nome, idade) VALUES (?, ?)";
            try {
                System.out.print("Digite o nome do aluno: ");
                String nome = scanner.nextLine();
                System.out.print("Digite a idade do aluno: ");
                int idade = scanner.nextInt();
                scanner.nextLine();  // Consumir quebra de linha

                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setInt(2, idade);
                stmt.executeUpdate();

                System.out.println("Aluno inserido com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao inserir dados: " + e.getMessage());
            } finally {
                fecharConexao(conexao);
            }
        }
    }

    // Método para ler alunos do banco de dados
    private static void lerAlunos() {
        Connection conexao = ConexaoBD.conectar();
        if (conexao != null) {
            String sql = "SELECT id, nome, idade FROM alunos";
            try {
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                System.out.println("\nRegistros da tabela 'alunos':");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    int idade = rs.getInt("idade");
                    System.out.printf("ID: %d, Nome: %s, Idade: %d%n", id, nome, idade);
                }
            } catch (SQLException e) {
                System.err.println("Erro ao ler dados: " + e.getMessage());
            } finally {
                fecharConexao(conexao);
            }
        }
    }

    // Método para atualizar um aluno no banco de dados
    private static void atualizarAluno(Scanner scanner) {
        Connection conexao = ConexaoBD.conectar();
        if (conexao != null) {
            String sql = "UPDATE alunos SET nome = ?, idade = ? WHERE id = ?";
            try {
                System.out.print("Digite o ID do aluno que deseja atualizar: ");
                int id = scanner.nextInt();
                scanner.nextLine();  // Consumir quebra de linha

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
                    System.out.println("Aluno atualizado com sucesso!");
                } else {
                    System.out.println("Nenhum aluno encontrado com o ID especificado.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao atualizar dados: " + e.getMessage());
            } finally {
                fecharConexao(conexao);
            }
        }
    }

    // Método para deletar um aluno do banco de dados
    private static void deletarAluno(Scanner scanner) {
        Connection conexao = ConexaoBD.conectar();
        if (conexao != null) {
            String sql = "DELETE FROM alunos WHERE id = ?";
            try {
                System.out.print("Digite o ID do aluno que deseja deletar: ");
                int id = scanner.nextInt();
                scanner.nextLine();  // Consumir quebra de linha

                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, id);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Aluno deletado com sucesso!");
                } else {
                    System.out.println("Nenhum aluno encontrado com o ID especificado.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao deletar dados: " + e.getMessage());
            } finally {
                fecharConexao(conexao);
            }
        }
    }

    // Método auxiliar para fechar a conexão
    private static void fecharConexao(Connection conexao) {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
