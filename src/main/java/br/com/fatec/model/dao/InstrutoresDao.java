/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.fatec.model.dao;
        
import br.com.fatec.model.entities.Instrutores;
import br.com.fatec.model.entities.Cursos;
import br.com.fatec.model.entities.Estado;
import br.com.fatec.model.persistencia.Banco;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Rafael
 */

public class InstrutoresDao 
        implements DAO<Instrutores> {

    //variaveis auxiliares
    //permite o uso de comandos DML (select, insert, delete e update) para
    //acessar nosso SGBD
    private java.sql.PreparedStatement pst;
   
    //permite armazenar um conjunto de dados vindo do SGBD para ser
    //manipulado
    private java.sql.ResultSet rs;
    
    //representar os dados do  meu negócio
    private Instrutores instrutor; //meu MODEL   
    
    @Override
    public boolean insere(Instrutores obj) throws SQLException {
        String sql = "INSERT INTO instrutor (Nome, Email, Nascimento, Graduacao, Faculdade, cursosId, estadosId) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?)"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Instrutores com o comando INSERT
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getEmail());
        pst.setDate(3, java.sql.Date.valueOf(obj.getNascimento()));
        pst.setString(4, obj.getGraduacao());
        pst.setString(5, obj.getFaculdade());
        pst.setInt(6, obj.getCurso().getId());
        pst.setInt(7, obj.getEstado().getId());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionou ou nao
        return res != 0;
        
    }

    @Override
    public boolean remove(Instrutores obj) throws SQLException {
        String sql = "DELETE FROM instrutor WHERE Id = ?"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Instrutores com o comando DELETE
        pst.setInt(1, obj.getId());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionoou ou nao
        return res != 0;
    }

    @Override
    public boolean altera(Instrutores obj) throws SQLException {
        String sql = "UPDATE instrutor SET Nome = ?, Email = ?, Nascimento = ?, Graduacao = ?, Faculdade = ?, cursosId = ?, estadosId = ? "
                + "WHERE Id = ?"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Cursos com o comando UPDATE
        pst.setInt(8, obj.getId());
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getEmail());
        pst.setDate(3, java.sql.Date.valueOf(obj.getNascimento()));
        pst.setString(4, obj.getGraduacao());
        pst.setString(5, obj.getFaculdade());
        pst.setInt(6, obj.getCurso().getId());
        pst.setInt(7, obj.getEstado().getId());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionoou ou nao
        return res != 0;
    }

    /**
     * Busca um dado de instrutor baseado em seu nome
     * @param obj
     * @return
     * @throws SQLException 
     */
    @Override
    public Instrutores busca(Instrutores obj) throws SQLException {
        String sql = "SELECT * FROM instrutor "
                + "WHERE Nome LIKE %?% " + 
                "JOIN cursos ON instrutor.cursosId = cursos.Id " + 
                "JOIN estados ON instrutor.estadosId = estados.Id "; //a ? indica parametros
        
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
            instrutor.setId(rs.getInt("Id"));
            instrutor.setNome(rs.getString("Nome"));
            instrutor.setEmail(rs.getString("Email"));
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
            instrutor.setNascimento(LocalDate.parse(rs.getString("Nascimento"), formatter));
            
            instrutor.setGraduacao(rs.getString("Graduacao"));
            instrutor.setFaculdade(rs.getString("Faculdade"));
            
            Cursos curso = new Cursos(rs.getInt("cursosId"), rs.getString("cursos.Nome"), rs.getString("cursos.Categoria"));
            instrutor.setCurso(curso);
            
            Estado estado = new Estado(rs.getInt("estadosId"), rs.getString("estados.Estado"), rs.getString("estados.Uf"));
            instrutor.setEstado(estado);
            
        }
        else {
            //não encontrou o registro solicitado
            instrutor = null;
        }
                
        //fecha a conexao
        Banco.desconectar();
        
        //devolve o objeto instrutor
        return instrutor;

    }

    @Override
    public Collection<Instrutores> lista(String criterio) throws SQLException {
        //cria uma lista para armazenar os dados vindos do banco
        ArrayList<Instrutores> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM instrutor ";

        sql += " JOIN cursos ON instrutor.cursosId = cursos.Id " + 
              "JOIN estados ON instrutor.estadosId = estados.Id ";

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
            instrutor = new Instrutores();
            
            //mover os dados do resultSet para o objeto proprietário
            instrutor.setId(rs.getInt("Id"));
            instrutor.setNome(rs.getString("Nome"));
            instrutor.setEmail(rs.getString("Email"));
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
            instrutor.setNascimento(LocalDate.parse(rs.getString("Nascimento"), formatter));
            
            instrutor.setGraduacao(rs.getString("Graduacao"));
            instrutor.setFaculdade(rs.getString("Faculdade"));
            // instrutor.setCurso(rs.getInt("cursosId"));
            // instrutor.setEstado(rs.getInt("estadosId"));

            Cursos curso = new Cursos(rs.getInt("cursosId"), rs.getString("cursos.Nome"), rs.getString("cursos.Categoria"));
            instrutor.setCurso(curso);
            
            Estado estado = new Estado(rs.getInt("estadosId"), rs.getString("estados.Estado"), rs.getString("estados.Uf"));
            instrutor.setEstado(estado);
            
            //move o objeto para a coleção
            lista.add(instrutor);
        }
                
        //fecha a conexao
        Banco.desconectar();
        
        //devolve o objeto instrutor
        return lista;
        
    }
    
}

