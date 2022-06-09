/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.Controller.InstrutoresDataController;
import br.com.fatec.model.entities.Cursos;
import br.com.fatec.model.entities.Instrutores;
import br.com.fatec.model.entities.Estado;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.scene.image.Image;

/**
 *
 * @author Rafael
 */
public class InstrutoresData extends Application {

    public static Stage tela;
    private int action;
    private Instrutores instrutor;
    
    //recebe o dado da tela anterior
    public InstrutoresData(int act, int Id, String nome, String email, 
            LocalDate nascimento, String graduacao, String faculdade,
            Cursos curso, Estado estado) {
        this.action = act;
        
        instrutor = new Instrutores();
        this.instrutor.setId(Id);
        this.instrutor.setNome(nome);
        this.instrutor.setEmail(email);
        this.instrutor.setNascimento(nascimento);
        this.instrutor.setGraduacao(graduacao);
        this.instrutor.setFaculdade(faculdade);
        this.instrutor.setCurso(curso);
        this.instrutor.setEstado(estado);
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
                App.class.getResource("InstrutoresData.fxml"));
        //efetua o carregamento na memória
        Parent root = fxmlLoader.load();
        
        //obtem o acesso ao objeto controller do formulario
        InstrutoresDataController controler = fxmlLoader.getController();
        
        //passa os dados necessários para a próxima tela
        controler.setAction(action);
        controler.setInstrutor(instrutor);
        controler.atualiza();
        
        Scene scene = new Scene(root, 480, 450);
        
        tela.setScene(scene);
        tela.showAndWait();
    }
    
}
