package se.dandel.lednotification.droid; /**
 * Created by gaston on 2017-06-13.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Droid extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/droid.fxml"));

        Scene scene = new Scene(root, 500, 400);

        stage.setTitle("se.dandel.lednotification.droid.Droid notification simulator");
        stage.setScene(scene);
        stage.show();
    }
}
