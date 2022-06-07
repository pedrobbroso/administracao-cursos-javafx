/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.App;
import br.com.fatec.model.entities.Cursos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pedro
 */
public class CursosListController implements Initializable {
    
    @FXML
    private TableView<Cursos> tableViewCursos;
    
    @FXML
    private TableColumn<Cursos, Integer> tableColumnId;
    
    @FXML
    private TableColumn<Cursos, String> tableColumnNome;
    
    @FXML
    private TableColumn<Cursos, String> tableColumnCategoria;
    
    @FXML
    private Button btNew;
    
    @FXML
    public void onBtNewAction() {
        System.out.println("onBtNewAction");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }    

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        
        Stage stage = (Stage) App.getScene().getWindow();
        tableViewCursos.prefHeightProperty().bind(stage.heightProperty());
    }
    
}
