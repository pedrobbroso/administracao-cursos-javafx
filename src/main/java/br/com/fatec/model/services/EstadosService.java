/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model.services;

import br.com.fatec.model.entities.Estado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pedro
 */
public class EstadosService {
    
    //Listando id, curso e categoria
    public List<Estado> findAll() {
		List<Estado> list = new ArrayList<>();
		list.add(new Estado(1, "Acre", "AC"));
		list.add(new Estado(2, "Alagoas", "AL"));
		list.add(new Estado(3, "Amazonas", "AM"));
                list.add(new Estado(4, "Amapá", "AP"));
                list.add(new Estado(5, "Bahia", "BA"));
                list.add(new Estado(6, "Ceará", "CE"));
                list.add(new Estado(7, "Distrito Federal", "DF"));
                list.add(new Estado(8, "Espírito Santo", "ES"));
                list.add(new Estado(9, "Goiás", "GO"));
                list.add(new Estado(10, "Maranhão", "MA"));
                list.add(new Estado(11, "Minas Gerais", "MG"));
                list.add(new Estado(12, "Mato Grosso do Sul", "MS"));
                list.add(new Estado(13, "Mato Grosso", "MT"));
                list.add(new Estado(14, "Pará", "PA"));
                list.add(new Estado(15, "Paraíba", "PB"));
                list.add(new Estado(16, "Pernambuco", "PE"));
                list.add(new Estado(17, "Piauí", "PI"));
                list.add(new Estado(18, "Paraná", "PR"));
                list.add(new Estado(19, "Rio de Janeiro", "RJ"));
                list.add(new Estado(20, "Rio Grande do Norte", "RN"));
                list.add(new Estado(21, "Rondônia", "RO"));
                list.add(new Estado(22, "Roraima", "RR"));
                list.add(new Estado(23, "Rio Grande do Sul", "RS"));
                list.add(new Estado(24, "Santa Catarina", "SC"));
                list.add(new Estado(25, "Sergipe", "SE"));
                list.add(new Estado(26, "São Paulo", "SP"));
                list.add(new Estado(27, "Tocantins", "TO"));
		return list;
	}
    
}
