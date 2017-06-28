package se.dandel.lednotification.droid; /**
 * Created by gaston on 2017-06-13.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Droid extends Application {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        launch(args);
    }

    private DroidController controllerHandle;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/droid.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 500, 400);

        stage.setTitle("se.dandel.lednotification.droid.Droid notification simulator");
        stage.setScene(scene);
        stage.show();

        controllerHandle = loader.getController();
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        LOGGER.debug("Shutting down initiated");
        controllerHandle.shutdown();
        LOGGER.debug("Shutting down completed");
    }
}
