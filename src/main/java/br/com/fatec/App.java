package br.com.fatec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static Stage fakeStage;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        fakeStage = stage;
        scene = new Scene(loadFXML("login"));
        //scene = new Scene(loadFXML("mainView"));
        stage.setScene(scene);       
        stage.getIcons().add(new Image("file:src/main/resources/images/cap.png"));
        stage.setTitle("Tech Cursos");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        fakeStage.getScene().setRoot(pane);
    }
    
    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }

}