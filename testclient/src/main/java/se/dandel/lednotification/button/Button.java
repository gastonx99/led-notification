package se.dandel.lednotification.button;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class Button extends Application {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private ButtonController controllerHandle;

    public static void main(String[] args) {
        launch(args);
    }

    private ExecutorService internetButtonExecutor;

    private ButtonService buttonService;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/button.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Internet Button simulator");
        stage.setScene(scene);
        stage.show();

        controllerHandle = loader.getController();

        buttonService = new ButtonService(controllerHandle.getCircles());
        buttonService.setup();
        outerLoop();
    }

    private void outerLoop() {
        internetButtonExecutor = Executors.newSingleThreadExecutor();
        internetButtonExecutor.submit(() -> {
                    try {
                        while (true) {
                            buttonService.innerLoop();
                            sleep(100);
                        }
                    } catch (InterruptedException e) {
                        LOGGER.debug("Sleep interrupted");
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
        );
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        LOGGER.debug("Shutting down initiated");
        internetButtonExecutor.shutdownNow();
        LOGGER.debug("Shutting down completed");
    }

}
