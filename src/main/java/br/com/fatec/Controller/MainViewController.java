/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.Controller.CursosListController;
import br.com.fatec.Controller.InstrutoresListController;
import br.com.fatec.Controller.TurmasListController;
import gui.util.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author pedro
 */
public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemCurso;

    @FXML
    private MenuItem menuItemInstrutor;

    @FXML
    private MenuItem menuItemTurma;

    @FXML
    private MenuItem menuItemAbout;

    public void onMenuItemCursoAction() {
        carregaView("CursosList", (CursosListController cursosListController) -> {
            cursosListController.updateTableView();
        });
    }
    
    public void onMenuItemInstrutorAction() {
        System.out.println("onMenuItemInstrutorAction");
        carregaView("InstrutoresList", (InstrutoresListController intrutoresListController) -> {
            intrutoresListController.updateTableView();
        });
    }

    public void onMenuItemTurmaAction() {
        System.out.println("onMenuItemTurmaAction");
        carregaView("TurmasList", (TurmasListController turmasListController) -> {
            turmasListController.updateTableView();
        });
    }

    public void onMenuItemAboutAction() {
        carregaView("about", x -> {});
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private synchronized <T> void carregaView(String nomeAbsoluto, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(nomeAbsoluto + ".fxml"));
            VBox newVbox = loader.load();
            
            Scene scene = App.getScene();
            VBox mainVBox = (VBox) ((ScrollPane) scene.getRoot()).getContent();
            
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVbox.getChildren());
            
            T controller = loader.getController();
            initializingAction.accept(controller);
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    /*
    CursosListController cursosListController = loader.getController();
    cursosListController.setCursosService(new CursosService());
    cursosListController.updateTableView();
    */
}
