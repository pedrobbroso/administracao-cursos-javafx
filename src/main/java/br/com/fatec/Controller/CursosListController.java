/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.App;
import br.com.fatec.CursosData;
import br.com.fatec.model.entities.Cursos;
import br.com.fatec.model.dao.CursosDao;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pedro/rafael
 */
public class CursosListController implements Initializable {
    
    private Stage stageFree;
    private CursosDao dao;
    private ObservableList<Cursos> observableList;
     
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
    private Button btEdit;
     
    @FXML
    private Button btRemove;   
   
    
    @FXML
    public void onBtNewAction() {
        System.out.println("onBtNewAction");
        try
        {
            //Cria um objeto curso com os dados em branco
            CursosData curso = new CursosData(0, 0, "", "");   
            curso.start(stageFree);
            updateTableView();
        }
        catch(Exception e){
            
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    e.getMessage(),
                    ButtonType.OK);
            
            alerta.showAndWait();
            
        }
    }
    
    @FXML
    public void onBtEditAction() {
        System.out.println("onBtEditAction");
       
        try
        {
            Cursos p = tableViewCursos.getSelectionModel().getSelectedItem();
            //Cria um objeto cursoData com os dados obtidos 
            CursosData curso = new CursosData(1, p.getId(), p.getNome(), p.getCategoria());   
            curso.start(stageFree);   
            updateTableView();
        }
        catch(Exception e){
            
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    e.getMessage(),
                    ButtonType.OK);
            
            alerta.showAndWait();
            
        }

    }
    
    @FXML
    public void onBtRemoveAction() {
        System.out.println("onBtRemoveAction");
        Cursos p = tableViewCursos.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Deseja remover o item selecionado?",
                    ButtonType.YES, ButtonType.NO);
            
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            try {
                
                if (dao.remove(p)) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Curso");
                    alerta.setContentText("Registro apagado com Sucesso!!!");

                    alerta.showAndWait(); //exibe a mensagem
                    updateTableView();
                }
                else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Curso");
                    alerta.setContentText("Erro na Gravaçao");

                    alerta.showAndWait(); //exibe a mensagem
                }
                }
                catch (SQLException ex) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Curso");
                    alerta.setContentText("Erro na Gravaçao: " +
                                ex.getMessage() + "\n" + ex.getStackTrace().toString());

                    alerta.showAndWait(); //exibe a mensagem
            }
        }
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
        
        stageFree = new Stage();
        dao = new CursosDao();
    }
    
    public void updateTableView() {
        
        tableViewCursos.setItems(preencheTabela());
        tableViewCursos.refresh();
        
    }
    
    private ObservableList<Cursos> preencheTabela() {
        dao = new CursosDao();
        ObservableList<Cursos> cursos
            = FXCollections.observableArrayList();
        
        try {
            cursos.addAll(dao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        
        return cursos;
    }
    
}
