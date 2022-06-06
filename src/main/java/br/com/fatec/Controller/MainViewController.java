/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
        System.out.println("onMenuItemAboutAction");
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
