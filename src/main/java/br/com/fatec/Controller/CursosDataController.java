/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.model.dao.CursosDao;
import br.com.fatec.model.entities.Cursos;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rafae
 */
public class CursosDataController implements Initializable {

    @FXML
    private Button btOk;
    @FXML
    private Button btCancel;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCategoria;

    private int action;
    private Cursos curso;
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public int getAction() {
        return action;
    }
    
    public void setAction(int act) {
        this.action = act;
    }
    
    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }
     
    public void atualiza() {
        txtNome.setText(curso.getNome());
        txtCategoria.setText(curso.getCategoria());
    }
     
    @FXML
    private void onBtOkAction(ActionEvent event) {
        
        String textNome = txtNome.getText().trim();
        String textCategoria = txtCategoria.getText().trim();
    
        if(textNome.isBlank()){
             Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "O campo nome não pode ser vazio!",
                    ButtonType.OK);
 
            alerta.showAndWait();
            return;
        }
        
        if(textCategoria.isBlank()){
             Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "O campo categoria não pode ser vazio!",
                    ButtonType.OK);
            
            alerta.showAndWait();
            return;
        }
           
        CursosDao dao = new CursosDao();

        //joga os dados da tela para o obj        
        curso.setNome(txtNome.getText());
        curso.setCategoria(txtCategoria.getText());

        try {
            //vamos gravar
            if(action == 1){
                if (dao.altera(curso)) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Curso");
                    alerta.setContentText("Dados Atualizados com Sucesso!!!");

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
            else {
                if (dao.insere(curso)) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Curso");
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
                alerta.setHeaderText("Curso");
                alerta.setContentText("Erro na Gravaçao: " +
                            ex.getMessage() + "\n" + ex.getStackTrace().toString());

                alerta.showAndWait(); //exibe a mensagem
        }
        
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void onBtCancelAction(ActionEvent event) {
       // Chamar a instancia da janela e fechar
       Stage stage = (Stage) btCancel.getScene().getWindow();
       stage.close();
    }
    
}
