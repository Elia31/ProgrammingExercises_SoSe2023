package com.example.carsellingplatform;

import com.example.carsellingplatform.backend.util.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CarSellingPlatform");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


        //datenbank verbindung trennen
        stage.setOnCloseRequest(event -> {
            DatabaseHandler.getInstance().closeConnection();
        });

    }

    public static void main(String[] args) {
        launch();
    }

}
