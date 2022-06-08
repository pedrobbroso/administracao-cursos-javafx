/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.App;
import br.com.fatec.TurmasData;
import br.com.fatec.model.dao.TurmasDao;
import br.com.fatec.model.entities.Cursos;
import br.com.fatec.model.entities.Instrutores;
import br.com.fatec.model.entities.Turmas;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author rafae
 */
public class TurmasListController implements Initializable {
   
    private Stage stageFree;
    private TurmasDao dao;
    private ObservableList<Turmas> observableList;
        
    @FXML
    private Button btNew;
    @FXML
    private Button btEdit;
    @FXML
    private Button btRemove;
    @FXML
    private TableView<Turmas> tableViewTurmas;
    @FXML
    private TableColumn<Turmas, Integer> tableColumnId;
    @FXML
    private TableColumn<Turmas, String> tableColumnPeriodo;
    @FXML
    private TableColumn<Turmas, String> tableColumnSala;
    @FXML
    private TableColumn<Turmas, Cursos> tableColumnCurso;
    @FXML
    private TableColumn<Turmas, Instrutores> tableColumnInstrutor;
    @FXML
    private TextField txtFiltro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }    

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        tableColumnSala.setCellValueFactory(new PropertyValueFactory<>("sala"));
        tableColumnCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        tableColumnInstrutor.setCellValueFactory(new PropertyValueFactory<>("instrutor"));
        
        Stage stage = (Stage) App.getScene().getWindow();
        tableViewTurmas.prefHeightProperty().bind(stage.heightProperty());
        
        stageFree = new Stage();
        dao = new TurmasDao();
    }
    
    public void updateTableView() {
        
        tableViewTurmas.setItems(preencheTabela());
        tableViewTurmas.refresh();
        
    }
    
    private ObservableList<Turmas> preencheTabela() {
        dao = new TurmasDao();
        ObservableList<Turmas> turmas
            = FXCollections.observableArrayList();
        
        try {
            turmas.addAll(dao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        
        return turmas;
    }
    
  
     public void searchTableView() {
       
        String filt = txtFiltro.getText().trim().toLowerCase();
        
        tableViewTurmas.setItems(searchTabela(filt));
        tableViewTurmas.refresh();
        
    }
    
    private ObservableList<Turmas> searchTabela(String filtro) {
        dao = new TurmasDao();
        ObservableList<Turmas> turmas
            = FXCollections.observableArrayList();
        
        try {
         
            String sql = "";

            if(!filtro.equals("")){
                sql += "LOWER(TRIM(instrutor.Nome)) LIKE '%" + filtro + "%' OR LOWER(TRIM(cursos.Nome)) LIKE '%" + filtro + "%'";
            }

            
            turmas.addAll(dao.lista(sql));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        
        return turmas;
    }
    

    @FXML
    private void onBtNewAction(ActionEvent event) {
        System.out.println("onBtNewAction");
        try
        {
            //Cria um objeto curso com os dados em branco
            Cursos curso = new Cursos(0, "Nenhum", "");
            Instrutores instrutor = new Instrutores(0, "Nenhum", "");
                      
            TurmasData turma = new TurmasData(0, 0, "Manhã", "", curso, instrutor);   
            turma.start(stageFree);
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
    private void onBtEditAction(ActionEvent event) {
       System.out.println("onBtEditAction");
        try
        {
            Turmas p = tableViewTurmas.getSelectionModel().getSelectedItem();
            //Cria um objeto cursoData com os dados obtidos                        
            TurmasData turma = new TurmasData(1, p.getId(), p.getPeriodo(), p.getSala(), p.getCurso(), p.getInstrutor());   
            turma.start(stageFree);   
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
    private void onBtRemoveAction(ActionEvent event) {
        System.out.println("onBtRemoveAction");
        Turmas p = tableViewTurmas.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Deseja remover o item selecionado?",
                    ButtonType.YES, ButtonType.NO);

        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            try {

                if (dao.remove(p)) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Turma");
                    alerta.setContentText("Registro apagado com Sucesso!!!");

                    alerta.showAndWait(); //exibe a mensagem
                    updateTableView();
                }
                else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Turma");
                    alerta.setContentText("Erro na Gravaçao");

                    alerta.showAndWait(); //exibe a mensagem
                }
                }
                catch (SQLException ex) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Turma");
                    alerta.setContentText("Erro na Gravaçao: " +
                                ex.getMessage() + "\n" + ex.getStackTrace().toString());

                    alerta.showAndWait(); //exibe a mensagem
            }
        }

    }

    @FXML
    private void onTxtFiltroKeyTyped(KeyEvent event) {
        searchTableView();
    }
    
}
