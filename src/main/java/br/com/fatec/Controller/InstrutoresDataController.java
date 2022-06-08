/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.model.dao.CursosDao;
import br.com.fatec.model.dao.InstrutoresDao;
import br.com.fatec.model.entities.Instrutores;
import br.com.fatec.model.entities.Cursos;
import br.com.fatec.model.entities.Estado;
import br.com.fatec.model.services.EstadosService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

/**
 * FXML Controller class
 *
 * @author rafae
 */
public class InstrutoresDataController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtGraduacao;
    @FXML
    private TextField txtFaculdade;
    @FXML
    private ComboBox<Cursos> cbCurso;
    @FXML
    private DatePicker dtpNasc;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private Button btOk;
    @FXML
    private Button btCancel;

    private int action;
    private Instrutores instrutor;
    private CursosDao cursosdao;
    private EstadosService estadosService;
  
    ObservableList<Cursos> cursos = FXCollections.observableArrayList();
    ObservableList<Estado> estados = FXCollections.observableArrayList();
        
    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Instrutores getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutores instrutor) {
        this.instrutor = instrutor;
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
    
     private ObservableList<Estado> preencheEstados() {
        estadosService = new EstadosService();
        try {
            estados.addAll(estadosService.findAll());
        } catch (Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        return estados;
    }
     
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void atualiza() {
        
        txtNome.setText(instrutor.getNome());
        txtEmail.setText(instrutor.getEmail());
        dtpNasc.setValue(instrutor.getNascimento());
        txtGraduacao.setText(instrutor.getGraduacao());
        txtFaculdade.setText(instrutor.getFaculdade());
        
        cbCurso.setItems(preencheCursos());
        cbCurso.setValue(instrutor.getCurso());
        
        cbEstado.setItems(preencheEstados());
        cbEstado.setValue(instrutor.getEstado());
        
    }
    
    @FXML
    private void onBtOkAction(ActionEvent event) {
        
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        LocalDate nascimento = dtpNasc.getValue();
        String graduacao = txtGraduacao.getText().trim();
        String faculdade = txtFaculdade.getText().trim();
        Cursos tcurso = cbCurso.getValue();
        Estado testado = cbEstado.getValue();
                
        if(nome.isBlank()){
             Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "O nome não pode ser vazio!",
                    ButtonType.OK);
 
            alerta.showAndWait();
            return;
        }
        
        if(email.isBlank()){
             Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "O campo sala não pode ser vazio!",
                    ButtonType.OK);
            
            alerta.showAndWait();
            return;
        }
        
        if(nascimento == null){
             Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "O campo data não pode ser vazio!",
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
           
        if(testado.getId() == 0){
             Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "Necessário selecionar um estado!",
                    ButtonType.OK);
            
            alerta.showAndWait();
            return;
        }
         
        InstrutoresDao dao = new InstrutoresDao();

        //joga os dados da tela para o obj        
        instrutor.setNome(nome);
        instrutor.setEmail(email);
        instrutor.setNascimento(nascimento);
        instrutor.setGraduacao(graduacao);
        instrutor.setFaculdade(faculdade);
        instrutor.setCurso(tcurso);
        instrutor.setEstado(testado);
        
        try {
            //vamos gravar
            if(action == 1){
                if (dao.altera(instrutor)) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Instrutor");
                    alerta.setContentText("Dados Atualizados com Sucesso!!!");

                    alerta.showAndWait(); //exibe a mensagem
                }
                else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Instrutor");
                    alerta.setContentText("Erro na Gravaçao");

                    alerta.showAndWait(); //exibe a mensagem
                }
            }
            else {
                if (dao.insere(instrutor)) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Instrutor");
                    alerta.setContentText("Dados Gravados com Sucesso!!!");

                    alerta.showAndWait(); //exibe a mensagem
                }
                else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Mensagem");
                    alerta.setHeaderText("Instrutor");
                    alerta.setContentText("Erro na Gravaçao");

                    alerta.showAndWait(); //exibe a mensagem
                }
            }
            
        } catch (SQLException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Mensagem");
                alerta.setHeaderText("Instrutor");
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
