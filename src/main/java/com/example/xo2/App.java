//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.xo2;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private ArrayList<Game> games = new ArrayList();
    private static Scene scene;

    public App() {
    }

    public void start(Stage stage) throws IOException {
        new Server(this.games);
        scene = new Scene(loadFXML("host"), 640.0, 480.0);
        stage.setScene(scene);
        stage.setOnCloseRequest((event) -> {
            Platform.exit();
        });
        stage.show();
    }

    static void setRoot(FXMLLoader loader) throws IOException {
        scene.setRoot((Parent)loader.load());
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return (Parent)fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(new String[0]);
    }
}
