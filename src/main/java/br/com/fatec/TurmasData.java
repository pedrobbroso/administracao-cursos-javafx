/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec;

import br.com.fatec.Controller.TurmasDataController;
import br.com.fatec.model.entities.Cursos;
import br.com.fatec.model.entities.Turmas;
import br.com.fatec.model.entities.Instrutores;
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
public class TurmasData extends Application {

    public static Stage tela;
    private int action;
    private Turmas turma;
    
    //recebe o dado da tela anterior
    public TurmasData(int act, int Id, String periodo, String sala, Cursos curso, Instrutores instrutor) {
        this.action = act;
        
        turma = new Turmas();
        this.turma.setId(Id);
        this.turma.setPeriodo(periodo);
        this.turma.setSala(sala);
        this.turma.setCurso(curso);
        this.turma.setInstrutor(instrutor);
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
                App.class.getResource("TurmasData.fxml"));
        //efetua o carregamento na memória
        Parent root = fxmlLoader.load();
        
        //obtem o acesso ao objeto controller do formulario
        TurmasDataController controler = fxmlLoader.getController();
        
        //passa os dados necessários para a próxima tela
        controler.setAction(action);
        controler.setTurma(turma);
        controler.atualiza();
        
        Scene scene = new Scene(root, 480, 300);
        
        tela.setScene(scene);
        tela.showAndWait();
    }
    
}
