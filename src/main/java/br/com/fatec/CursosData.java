/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.Controller.CursosDataController;
import br.com.fatec.model.entities.Cursos;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Rafael
 */
public class CursosData extends Application {

    public static Stage tela;
    private int action;
    private Cursos curso;
    
    //recebe o dado da tela anterior
    public CursosData(int act, int Id, String nome, String categoria) {
        this.action = act;
        
        curso = new Cursos();
        this.curso.setId(Id);
        this.curso.setNome(nome);
        this.curso.setCategoria(categoria);
    }
    
    public static void setStage(Stage t) {
        tela = t;
    }
    
    @Override
    public void start(Stage tela) throws Exception {
        //passa o Stage recebido para a variavel
        
        tela.getIcons().add(new Image("file:src/main/resources/images/cap.png"));
        //local da classe
        setStage(tela);
        
        //carrega o próximo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("CursosData.fxml"));
        //efetua o carregamento na memória
        Parent root = fxmlLoader.load();
        
        //obtem o acesso ao objeto controller do formulario
        CursosDataController controler = fxmlLoader.getController();
        
        //passa os dados necessários para a próxima tela
        controler.setAction(action);
        controler.setCurso(curso);
        controler.atualiza();
        
        Scene scene = new Scene(root, 480, 240);
        
        tela.setScene(scene);
        tela.showAndWait();
    }
    
}
