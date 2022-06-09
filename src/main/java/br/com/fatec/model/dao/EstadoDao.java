/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.fatec.model.dao;
        
import br.com.fatec.model.entities.Cursos;
import br.com.fatec.model.persistencia.Banco;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Rafael
 */

public class CursosDao 
        implements DAO<Cursos> {

    //variaveis auxiliares
    //permite o uso de comandos DML (select, insert, delete e update) para
    //acessar nosso SGBD
    private java.sql.PreparedStatement pst;
    
    //permite armazenar um conjunto de dados vindo do SGBD para ser
    //manipulado
    private java.sql.ResultSet rs;
    
    //representar os dados do  meu negócio
    private Cursos cursos; //meu MODEL   
    
    @Override
    public boolean insere(Cursos obj) throws SQLException {
        String sql = "INSERT INTO cursos (Nome, Categoria) " +
                " VALUES (?, ?)"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Cursos com o comando INSERT
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getCategoria());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionou ou nao
        return res != 0;
        
    }

    @Override
    public boolean remove(Cursos obj) throws SQLException {
        String sql = "DELETE FROM cursos WHERE Id = ?"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Cursos com o comando DELETE
        pst.setInt(1, obj.getId());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionoou ou nao
        return res != 0;
    }

    @Override
    public boolean altera(Cursos obj) throws SQLException {
        String sql = "UPDATE cursos SET Nome = ?, Categoria = ? "
                + "WHERE Id = ?"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Cursos com o comando UPDATE
        pst.setInt(3, obj.getId());
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getCategoria());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionoou ou nao
        return res != 0;
    }

    /**
     * Busca um dado de curso baseado em seu nome
     * @param obj
     * @return
     * @throws SQLException 
     */
    @Override
    public Cursos busca(Cursos obj) throws SQLException {
        String sql = "SELECT * FROM cursos "
                + "WHERE Nome LIKE %?%"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Cursos com o comando UPDATE
        pst.setString(1, obj.getNome());
        
        //executar o comando
        rs = pst.executeQuery(); //esse método serve para SELECT
        
        //verificar se trouxe algum registro
        //rs.next() faz a leitura do próximo registro, se existir devolve true
        //se nao devolve false
        if(rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto proprietário
            cursos.setId(rs.getInt("Id"));
            cursos.setNome(rs.getString("Nome"));
            cursos.setCategoria(rs.getString("Categoria"));
        }
        else {
            //não encontrou o registro solicitado
            cursos = null;
        }
                
        //fecha a conexao
        Banco.desconectar();
        
        //devolve o objeto proprietario
        return cursos;

    }

    @Override
    public Collection<Cursos> lista(String criterio) throws SQLException {
        //cria uma lista para armazenar os dados vindos do banco
        ArrayList<Cursos> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM cursos ";

        //precisa fazer filtro para listagem
        if(criterio != null && criterio.length() > 0) {
            sql += " WHERE " + criterio;
        }
        
        //abre a conexao com o banco
        Banco.conectar();
        
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //executar o comando
        rs = pst.executeQuery(); //esse método serve para SELECT
        
        //Varre todo o resultado da consulta e coloca cada registro dentro
        //de um objeto e coloca o objeto dentro da coleção
        while(rs.next()) {
            //criar o objeto
            cursos = new Cursos();
            
            //mover os dados do resultSet para o objeto proprietário
            cursos.setId(rs.getInt("Id"));
            cursos.setNome(rs.getString("Nome"));
            cursos.setCategoria(rs.getString("Categoria"));
            //move o objeto para a coleção
            lista.add(cursos);
        }
                
        //fecha a conexao
        Banco.desconectar();
        
        //devolve o objeto proprietario
        return lista;
        
    }
    
}

