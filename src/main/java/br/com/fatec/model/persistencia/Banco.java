/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model.persistencia;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author pedro
 */
public class Banco {
    
    //criar atributos
    public static String bancoDados, usuario, senha, servidor;
    public static int porta;

    //variavel de conexao
    public static java.sql.Connection conexao = null;

    //define valores padrão
    static {
        //mysql e mariaDB
        bancoDados = "javafx_cadastrocursos";
        usuario = "root";
        senha = "root";
        servidor = "localhost";
        porta = 3306;
    }

    //métodos
    public static void conectar() throws SQLException {
        //mysql
        String url = "jdbc:mariadb://" + servidor +
                     ":" + porta + "/" + bancoDados;

        conexao = DriverManager.getConnection(url, usuario, senha);
    }

    public static void desconectar() throws SQLException {
        conexao.close();
    }

    public static java.sql.Connection obterConexao() 
                throws SQLException {
        if (conexao == null) {
            throw new SQLException("Conexão está fechada..");
        } else {
            return conexao;
        }
    }
    
}
