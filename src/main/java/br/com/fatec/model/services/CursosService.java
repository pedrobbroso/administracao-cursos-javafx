/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model.services;

import br.com.fatec.model.entities.Cursos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pedro
 */
public class CursosService {
    
    //Listando id, curso e categoria
    public List<Cursos> findAll() {
		List<Cursos> list = new ArrayList<>();
		list.add(new Cursos(1, "Java", "Tecnologia"));
		list.add(new Cursos(2, "Arduíno", "Eletrônica"));
		list.add(new Cursos(3, "C", "Tecnologia"));
		return list;
	}
    
}
