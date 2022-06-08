/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.model.dao.InstrutoresDao;
import br.com.fatec.model.dao.CursosDao;
import br.com.fatec.model.dao.TurmasDao;
import br.com.fatec.model.entities.Turmas;
import br.com.fatec.model.entities.Cursos;
import br.com.fatec.model.entities.Instrutores;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rafae
 */
public class TurmasDataController implements Initializable {

    @FXML
    private TextField txtSala;
    @FXML
    private ComboBox<String> cbPeriodo;
    @FXML
    private ComboBox<Cursos> cbCurso;
    @FXML
    private ComboBox<Instrutores> cbInstrutor;
    @FXML
    private Button btCancel;
    @FXML
    private Button btOk;

    private int action;
    private Turmas turma;
    private CursosDao cursosdao;
    private InstrutoresDao instrutoresdao;
  
    ObservableList<String> periodos = FXCollections.observableArrayList();
    ObservableList<Cursos> cursos = FXCollections.observableArrayList();
    ObservableList<Instrutores> instrutores = FXCollections.observableArrayList();
     
    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Turmas getTurma() {
        return turma;
    }

    public void setTurma(Turmas turma) {
        this.turma = turma;
    }
        
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void atualiza() {

        periodos.add("Manhã");
        periodos.add("Tarde");
        periodos.add("Noite");
             
        cbPeriodo.setItems(periodos);
        cbPeriodo.setValue(turma.getPeriodo());
       
        txtSala.setText(turma.getSala());
        cbCurso.setItems(preencheCursos());
        cbCurso.setValue(turma.getCurso());
        
        cbInstrutor.setItems(preencheInstrutores());
        cbInstrutor.setValue(turma.getInstrutor());
        
    }
    
    private ObservableList<Cursos> preencheCursos() {
        cursosdao = new CursosDao();
        try {
            cursos.addAll(cursosdao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        return cursos;
    }
    
     private ObservableList<Instrutores> preencheInstrutores() {
        instrutoresdao = new InstrutoresDao();
        try {
            instrutores.addAll(instrutoresdao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        return instrutores;
    }
     
     
    @FXML
    private void onBtCancelAction(ActionEvent event) {
        // Chamar a instancia da janela e fechar
       Stage stage = (Stage) btCancel.getScene().getWindow();
       stage.close();
    }

    @FXML
    private void onBtOkAction(ActionEvent event) {
                      
        String periodo = cbPeriodo.getValue();
        String sala = txtSala.getText().trim();
        Cursos tcurso = cbCurso.getValue();
        Instrutores tinstrutor = cbInstrutor.getValue();
                
        if(periodo.isBlank()){
             Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "O período não pode ser vazio!",
                    ButtonType.OK);
 
            alerta.showAndWait();
            return;
        }
        
        if(sala.isBlank()){
             Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "O campo sala não pode ser vazio!",
                    ButtonType.OK);
            
            alerta.showAndWait();
            return;
        }
        
        if(tcurso.getId() == 0){
             Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Necessário selecionar um curso!",
                    ButtonType.OK);
            
            alerta.showAndWait();
            return;
        }
           
         if(tinstrutor.getId() == 0){
             Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Necessário selecionar um instrutor!",
                    ButtonType.OK);
            
            alerta.showAndWait();
            return;
        }
         
        TurmasDao dao = new TurmasDao();

        //joga os dados da tela para o obj        
        turma.setPeriodo(periodo);
        turma.setSala(sala);
        turma.setCurso(tcurso);
        turma.setInstrutor(tinstrutor);
        
        try {
            //vamos gravar
            if(action == 1){
                if (dao.altera(turma)) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Turma");
                    alerta.setContentText("Dados Atualizados com Sucesso!!!");

                    alerta.showAndWait(); //exibe a mensagem
                }
                else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Turma");
                    alerta.setContentText("Erro na Gravaçao");

                    alerta.showAndWait(); //exibe a mensagem
                }
            }
            else {
                if (dao.insere(turma)) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Turma");
                    alerta.setContentText("Dados Gravados com Sucesso!!!");

                    alerta.showAndWait(); //exibe a mensagem
                }
                else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Curso");
                    alerta.setContentText("Erro na Gravaçao");

                    alerta.showAndWait(); //exibe a mensagem
                }
            }
            
        } catch (SQLException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Mensagem");
                alerta.setHeaderText("Turma");
                alerta.setContentText("Erro na Gravaçao: " +
                            ex.getMessage() + "\n" + ex.getStackTrace().toString());

                alerta.showAndWait(); //exibe a mensagem
        }
        
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
        
        
    }
    
}
