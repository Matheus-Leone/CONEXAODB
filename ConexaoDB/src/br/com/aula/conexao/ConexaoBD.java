package br.com.aula.conexao;

//IMPORTS para utilização de métodos da biblioteca
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Criação de classe que irá realizar a conexão com o WAMP
public class ConexaoBD {

    // Definindo parâmetros da conexão (URL, USUÁRIO E SENHA WAMP) 
	private static final String URL = "jdbc:mysql://localhost:3306/aula_java_db";
    private static final String USUARIO = "root";
    private static final String SENHA = ""; // Coloque a senha do seu MySQL, se houver

    // Método para obter conexão com o banco
    public static Connection conectar() {
        Connection conexao = null;
        try {
            // Carregando o driver JDBC instalado anteriormente
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Tentando estabelecer conexão
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão realizada com sucesso!");

            //Caso ocorra algum erro, um desses 2 catch irão mostrar a mensagem e o porquê que não funcionou 
        } catch (ClassNotFoundException e) {
            System.err.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
        return conexao;
    }

    //Instanciar método conectar
    public static void main(String[] args) {
        // Testando a conexão
        conectar();
    }
}
