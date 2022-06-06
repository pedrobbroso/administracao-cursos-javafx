/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.util;

import javafx.scene.control.TextField;

/**
 *
 * @author pedro
 */
public class Constraints {

    public static void setTextFieldInteger(TextField txt) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) {
                txt.setText(oldValue);
            }
        });
    }

    public static void setTextFieldMaxLength(TextField txt, int max) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > max) {
                txt.setText(oldValue);
            }
        });
    }

    public static void setTextFieldDouble(TextField txt) {
        txt.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
                txt.setText(oldValue);
            }
        });
    }
}
