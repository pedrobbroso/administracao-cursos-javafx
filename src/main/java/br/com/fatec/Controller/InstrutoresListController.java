/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.App;
import br.com.fatec.InstrutoresData;
import br.com.fatec.model.dao.InstrutoresDao;
import br.com.fatec.model.entities.Cursos;
import br.com.fatec.model.entities.Estado;
import br.com.fatec.model.entities.Instrutores;
import br.com.fatec.model.services.EstadosService;
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
import java.time.LocalDate;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author rafae
 */
public class InstrutoresListController implements Initializable {

    private Stage stageFree;
    private InstrutoresDao dao;
    private ObservableList<Instrutores> observableList;
    private EstadosService estadosService;
    private ObservableList<Estado> estados;
    private Estado estadoSelect;
    @FXML
    private Button btNew;
    @FXML
    private Button btEdit;
    @FXML
    private Button btRemove;
    @FXML
    private TableView<Instrutores> tableViewInstrutores;
    @FXML
    private TableColumn<Instrutores, Integer> tableColumnId;
    @FXML
    private TableColumn<Instrutores, String> tableColumnNome;
    @FXML
    private TableColumn<Instrutores, String> tableColumnEmail;
    @FXML
    private TableColumn<Instrutores, LocalDate> tableColumnDataNasc;
    @FXML
    private TableColumn<Instrutores, String> tableColumnGraduacao;
    @FXML
    private TableColumn<Instrutores, String> tableColumnFaculdade;
    @FXML
    private TableColumn<Instrutores, Cursos> tableColumnCurso;
    @FXML
    private TableColumn<Instrutores, Estado> tableColumnEstado;
    @FXML
    private ComboBox<Estado> cbEstado;
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
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnDataNasc.setCellValueFactory(new PropertyValueFactory<>("nascimento"));
        tableColumnGraduacao.setCellValueFactory(new PropertyValueFactory<>("graduacao"));
        tableColumnFaculdade.setCellValueFactory(new PropertyValueFactory<>("faculdade"));
        tableColumnCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        tableColumnEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        Stage stage = (Stage) App.getScene().getWindow();
        tableViewInstrutores.prefHeightProperty().bind(stage.heightProperty());
        
        stageFree = new Stage();
        dao = new InstrutoresDao();
       
        cbEstado.setItems(preencheEstados());
        cbEstado.setValue(estadoSelect);
    }
    
    public void updateTableView() {
        
        tableViewInstrutores.setItems(preencheTabela());
        tableViewInstrutores.refresh();
        
    }
    
    private ObservableList<Instrutores> preencheTabela() {
        dao = new InstrutoresDao();
        ObservableList<Instrutores> instrutores
            = FXCollections.observableArrayList();
        
        try {
            instrutores.addAll(dao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        
        return instrutores;
    }
    
    private ObservableList<Estado> preencheEstados() {
        estados = FXCollections.observableArrayList();
        estadosService = new EstadosService();
        try {
            estadoSelect = new Estado(0, "Todos", "");
            estados.add(estadoSelect);
            estados.addAll(estadosService.findAll());
        } catch (Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        return estados;
    }
    
    
    public void searchTableView() {
       
        String filt = txtFiltro.getText().trim().toLowerCase();
        
        tableViewInstrutores.setItems(searchTabela(estadoSelect, filt));
        tableViewInstrutores.refresh();
        
    }
    
    private ObservableList<Instrutores> searchTabela(Estado estado, String filtro) {
        dao = new InstrutoresDao();
        ObservableList<Instrutores> instrutores
            = FXCollections.observableArrayList();
        
        try {
         
            String sql = "";
            
            if (estado.getId() != 0){
                if(!filtro.equals("")){
                    sql += "LOWER(TRIM(instrutor.Nome)) LIKE '%" + filtro + "%' AND instrutor.estadosId = " + estado.getId();
                }
                else {
                     sql += "instrutor.estadosId = " + estado.getId();
                }
            }
            else {
                if(!filtro.equals("")){
                    sql += "LOWER(TRIM(instrutor.Nome)) LIKE '%" + filtro + "%'";
                }
            }
            
            instrutores.addAll(dao.lista(sql));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        
        return instrutores;
    }
    
    
    @FXML
    private void onBtNewAction(ActionEvent event) {
        System.out.println("onBtNewAction");
        try
        {
            //Cria um objeto curso com os dados em branco
            Cursos curso = new Cursos(0, "Nenhum", "");
            Estado estado = new Estado(0, "Nenhum", "");
           
            InstrutoresData instrutor = new InstrutoresData(0, 0, "", "", LocalDate.now(), "", "", curso, estado);   
            instrutor.start(stageFree);
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
            Instrutores p = tableViewInstrutores.getSelectionModel().getSelectedItem();
            //Cria um objeto cursoData com os dados obtidos                        
            InstrutoresData instrutor = new InstrutoresData(1, p.getId(), p.getNome(), 
                    p.getEmail(), p.getNascimento(),  p.getGraduacao(), p.getFaculdade(),
                    p.getCurso(), p.getEstado());   
            
            instrutor.start(stageFree);   
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
        Instrutores p = tableViewInstrutores.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Deseja remover o item selecionado?",
                    ButtonType.YES, ButtonType.NO);

        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            try {

                if (dao.remove(p)) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Instrutores");
                    alerta.setContentText("Registro apagado com Sucesso!!!");

                    alerta.showAndWait(); //exibe a mensagem
                    updateTableView();
                }
                else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Instrutores");
                    alerta.setContentText("Erro na Gravaçao");

                    alerta.showAndWait(); //exibe a mensagem
                }
                }
                catch (SQLException ex) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Instrutores");
                    alerta.setContentText("Erro na Gravaçao: " +
                                ex.getMessage() + "\n" + ex.getStackTrace().toString());

                    alerta.showAndWait(); //exibe a mensagem
            }
        }
    }

    @FXML
    private void onCbEstadoAction(ActionEvent event) {
        estadoSelect = cbEstado.getValue();
        searchTableView();
    }


    @FXML
    private void onTxtFiltroKeyTyped(KeyEvent event) {
         searchTableView();
    }
    
}
