package pl.kwako.metroid_map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MetroidMap extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("view/metroidMap.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Metroid Map");
        primaryStage.setScene(scene);
        primaryStage.show();

        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());
    }

    public static void main(String[] args) {
        launch();
    }
}
