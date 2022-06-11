/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author pedro
 */
public class LoginController {

    public LoginController() {

    }

    @FXML
    private Button button;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();

    }

    private void checkLogin() throws IOException {
        App app = new App();
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            wrongLogin.setText("Sucesso!");

            app.changeScene("mainView.fxml");

        } else if (username.getText().isEmpty() && password.getText().isEmpty()) {
            wrongLogin.setText("Inválido");
        } else {
            wrongLogin.setText("Usuário ou senha inválida");
        }
    }

}
