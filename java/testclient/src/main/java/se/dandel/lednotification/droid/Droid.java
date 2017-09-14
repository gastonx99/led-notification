package se.dandel.lednotification.droid; /**
 * Created by gaston on 2017-06-13.
 */

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.lednotification.AlertSender;
import se.dandel.lednotification.BroadcastingAlertSender;
import se.dandel.lednotification.SmartPhoneAlertService;

public class Droid extends Application {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        launch(args);
    }

    private DroidController controllerHandle;

    @Override
    public void start(Stage stage) throws Exception {
        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bind(AlertSender.class).to(BroadcastingAlertSender.class);
            }
        };
        Injector injector = Guice.createInjector(module);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/droid.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> type) {
                return injector.getInstance(type);
            }
        });
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