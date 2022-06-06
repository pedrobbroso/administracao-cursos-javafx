/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import gui.util.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
        System.out.println("onMenuItemCursoAction");
    }

    public void onMenuItemInstrutorAction() {
        System.out.println("onMenuItemInstrutorAction");
    }

    public void onMenuItemTurmaAction() {
        System.out.println("onMenuItemTurmaAction");
    }

    public void onMenuItemAboutAction() {
        carregaView("about");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void carregaView(String nomeAbsoluto) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(nomeAbsoluto + ".fxml"));
            VBox newVbox = loader.load();
            Scene scene = App.getScene();
            VBox mainVBox = (VBox) ((ScrollPane) scene.getRoot()).getContent();
            
            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVbox.getChildren());
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error ao carregar a view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
