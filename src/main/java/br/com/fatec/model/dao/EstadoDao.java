/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.fatec.model.dao;
        
import br.com.fatec.model.entities.Estado;
import br.com.fatec.model.persistencia.Banco;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Rafael
 */

public class EstadoDao 
        implements DAO<Estado> {

    //variaveis auxiliares
    //permite o uso de comandos DML (select, insert, delete e update) para
    //acessar nosso SGBD
    private java.sql.PreparedStatement pst;
    
    //permite armazenar um conjunto de dados vindo do SGBD para ser
    //manipulado
    private java.sql.ResultSet rs;
    
    //representar os dados do  meu negócio
    private Estado estado; //meu MODEL   
    
    @Override
    public boolean insere(Estado obj) throws SQLException {
        String sql = "INSERT INTO estados (Estado, Uf) " +
                " VALUES (?, ?)"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto estados com o comando INSERT
        pst.setString(1, obj.getEstado());
        pst.setString(2, obj.getUf());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionou ou nao
        return res != 0;
        
    }

    @Override
    public boolean remove(Estado obj) throws SQLException {
        String sql = "DELETE FROM estados WHERE Id = ?"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto estados com o comando DELETE
        pst.setInt(1, obj.getId());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionoou ou nao
        return res != 0;
    }

    @Override
    public boolean altera(Estado obj) throws SQLException {
        String sql = "UPDATE cursos SET Estado = ?, Uf = ? "
                + "WHERE Id = ?"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto estados com o comando UPDATE
        pst.setInt(3, obj.getId());
        pst.setString(1, obj.getEstado());
        pst.setString(2, obj.getUf());
        
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
    public Estado busca(Estado obj) throws SQLException {
        String sql = "SELECT * FROM estados "
                + "WHERE Nome LIKE %?%"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto estados com o comando UPDATE
        pst.setString(1, obj.getEstado());
        
        //executar o comando
        rs = pst.executeQuery(); //esse método serve para SELECT
        
        //verificar se trouxe algum registro
        //rs.next() faz a leitura do próximo registro, se existir devolve true
        //se nao devolve false
        if(rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto estados
            estado.setId(rs.getInt("Id"));
            estado.setEstado(rs.getString("Estado"));
            estado.setUf(rs.getString("Uf"));
        }
        else {
            //não encontrou o registro solicitado
            estado = null;
        }
                
        //fecha a conexao
        Banco.desconectar();
        
        //devolve o objeto proprietario
        return estado;

    }

    @Override
    public Collection<Estado> lista(String criterio) throws SQLException {
        //cria uma lista para armazenar os dados vindos do banco
        ArrayList<Estado> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM estados ";

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
            estado = new Estado();
            
            //mover os dados do resultSet para o objeto estados
            estado.setId(rs.getInt("Id"));
            estado.setEstado(rs.getString("Estado"));
            estado.setUf(rs.getString("Uf"));
            //move o objeto para a coleção
            lista.add(estado);
        }
                
        //fecha a conexao
        Banco.desconectar();
        
        //devolve o objeto proprietario
        return lista;
        
    }
    
}

