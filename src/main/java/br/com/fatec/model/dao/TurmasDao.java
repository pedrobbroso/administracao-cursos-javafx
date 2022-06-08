/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.fatec.model.dao;
        
import br.com.fatec.model.entities.Turmas;
import br.com.fatec.model.entities.Instrutores;
import br.com.fatec.model.entities.Cursos;
import br.com.fatec.model.persistencia.Banco;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.time.LocalDate;

/**
 *
 * @author Rafael
 */

public class TurmasDao 
        implements DAO<Turmas> {

    //variaveis auxiliares
    //permite o uso de comandos DML (select, insert, delete e update) para
    //acessar nosso SGBD
    private java.sql.PreparedStatement pst;
    
    //permite armazenar um conjunto de dados vindo do SGBD para ser
    //manipulado
    private java.sql.ResultSet rs;
    
    //representar os dados do  meu negócio
    private Turmas turma; //meu MODEL   
    
    @Override
    public boolean insere(Turmas obj) throws SQLException {
        String sql = "INSERT INTO turma (Periodo, Sala, cursosId, instrutorId) " +
                " VALUES (?, ?, ?, ?)"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Turmas com o comando INSERT
        pst.setString(1, obj.getPeriodo());
        pst.setString(2, obj.getSala());
        pst.setInt(3, obj.getCurso().getId());
        pst.setInt(4, obj.getInstrutor().getId());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionou ou nao
        return res != 0;
        
    }

    @Override
    public boolean remove(Turmas obj) throws SQLException {
        String sql = "DELETE FROM turma WHERE Id = ?"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Turmas com o comando DELETE
        pst.setInt(1, obj.getId());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionoou ou nao
        return res != 0;
    }

    @Override
    public boolean altera(Turmas obj) throws SQLException {
        String sql = "UPDATE turma SET Periodo = ?, Sala = ?, cursosId = ?, instrutorId = ? "
                + "WHERE Id = ?"; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Turmas com o comando UPDATE
        pst.setInt(5, obj.getId());
        pst.setString(1, obj.getPeriodo());
        pst.setString(2, obj.getSala());
        pst.setInt(3, obj.getCurso().getId());
        pst.setInt(4, obj.getInstrutor().getId());
        
        //executar o comando
        int res = pst.executeUpdate(); //esse método serve para Insert, delete e update
        
        //fecha a conexao
        Banco.desconectar();
        
        //devolve se funcionoou ou nao
        return res != 0;
    }

    /**
     * Busca um dado de turma baseado em qualquer dado
     * @param obj
     * @return
     * @throws SQLException 
     */
    @Override
    public Turmas busca(Turmas obj) throws SQLException {
        String sql = "SELECT * FROM turma "
                + "WHERE Id LIKE %?% OR Periodo LIKE %?% OR Sala LIKE %?% " + 
                "JOIN cursos ON turma.cursosId = cursos.Id " + 
                "JOIN instrutor ON turma.instrutorId = instrutor.Id "; //a ? indica parametros
        
        //abre a conexao com o banco
        Banco.conectar();
        //preparar o comando PST
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //associar os dados do objeto Turmas com o comando UPDATE
        pst.setInt(1, obj.getId());
        pst.setString(2, obj.getPeriodo());
        pst.setString(3, obj.getSala());
        
        //executar o comando
        rs = pst.executeQuery(); //esse método serve para SELECT
        
        //verificar se trouxe algum registro
        //rs.next() faz a leitura do próximo registro, se existir devolve true
        //se nao devolve false
        if(rs.next()) {
            //mover os dados(campos da tab) do resultSet para o objeto proprietário
            turma.setId(rs.getInt("Id"));
            turma.setPeriodo(rs.getString("Periodo"));
            turma.setSala(rs.getString("Sala"));
            //            turma.setCurso(rs.getInt("cursosId"));
            //            turma.setInstrutor(rs.getInt("instrutorId"));

            Cursos curso = new Cursos(rs.getInt("cursosId"), rs.getString("cursos.Nome"), rs.getString("cursos.Categoria"));
            turma.setCurso(curso);
            
            Instrutores instrutor = new Instrutores(rs.getInt("instrutorId"), rs.getString("instrutor.Nome"), rs.getString("instrutor.Email"));
            
//            instrutor.setNascimento(LocalDate.parse(rs.getString("instrutor.Nascimento")));
//            instrutor.setGraduacao(rs.getString("instrutor.Graduacao"));
//            instrutor.setFaculdade(rs.getString("instrutor.Faculdade"));
//            
            turma.setInstrutor(instrutor);

        }
        else {
            //não encontrou o registro solicitado
            turma = null;
        }
                
        //fecha a conexao
        Banco.desconectar();
        
        //devolve o objeto turma
        return turma;

    }

    @Override
    public Collection<Turmas> lista(String criterio) throws SQLException {
        //cria uma lista para armazenar os dados vindos do banco
        ArrayList<Turmas> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM turma ";

        sql += "JOIN cursos ON turma.cursosId = cursos.Id " + 
             "JOIN instrutor ON turma.instrutorId = instrutor.Id ";
        
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
            turma = new Turmas();
            
            //mover os dados do resultSet para o objeto proprietário
            turma.setId(rs.getInt("Id"));
            turma.setPeriodo(rs.getString("Periodo"));
            turma.setSala(rs.getString("Sala"));
//            turma.setCurso(rs.getInt("cursosId"));
//            turma.setInstrutor(rs.getInt("instrutorId"));

            Cursos curso = new Cursos(rs.getInt("cursosId"), rs.getString("cursos.Nome"), rs.getString("cursos.Categoria"));
            turma.setCurso(curso);
            
            Instrutores instrutor = new Instrutores(rs.getInt("instrutorId"), rs.getString("instrutor.Nome"), rs.getString("instrutor.Email"));
//            instrutor.setNascimento(LocalDate.parse(rs.getString("instrutor.Nascimento")));
//            instrutor.setGraduacao(rs.getString("instrutor.Graduacao"));
//            instrutor.setFaculdade(rs.getString("instrutor.Faculdade"));
            
            turma.setInstrutor(instrutor);
            //move o objeto para a coleção
            lista.add(turma);
        }
                
        //fecha a conexao
        Banco.desconectar();
        
        //devolve o objeto proprietario
        return lista;
        
    }
    
}

