/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model.entities;

import br.com.fatec.model.entities.Instrutores;
import br.com.fatec.model.entities.Cursos;
/**
 *
 * @author rafael
 */
public class Turmas {

    private Integer id;
    private String periodo;
    private String sala;
    private Cursos curso;
    private Instrutores instrutor;
    
    //default constructor
    public Turmas() {
    }

    //constructor
    public Turmas(Integer id, String periodo, String sala) {
        this.id = id;
        this.periodo = periodo;
        this.sala = sala;
    }

    //getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public Instrutores getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutores instrutor) {
        this.instrutor = instrutor;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Turmas other = (Turmas) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + " - " + curso.getNome() + " - " + periodo + ", sala=" + sala;
    }
}
