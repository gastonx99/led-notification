import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ButtonController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @FXML
    private Circle westnorthwest;
    @FXML
    private Circle northnorthwest;
    @FXML
    private Circle north;
    @FXML
    private Circle northnortheast;
    @FXML
    private Circle eastnortheast;
    @FXML
    private Circle west;
    @FXML
    private Circle east;
    @FXML
    private Circle westsouthwest;
    @FXML
    private Circle southsouthwest;
    @FXML
    private Circle south;
    @FXML
    private Circle southsoutheast;
    @FXML
    private Circle eastsoutheast;
    @FXML
    private GridPane rootPane;

    private List<Circle> incomingCallLeds = new ArrayList<>();

    private ButtonState state = new ButtonState();

    public void initializeLeds() {
        incomingCallLeds.addAll(Arrays.asList(northnortheast, eastnortheast, east, eastsoutheast, southsoutheast, south, southsouthwest, westsouthwest, west, westnorthwest, northnorthwest));
        startStateRefresher();
    }

    private void startStateRefresher() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                handleButtonState();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }

    private void handleButtonState() throws InterruptedException {
        while (true) {
            if (state.isIncomingCall()) {
                Circle led = incomingCallLeds.iterator().next();
                Color newColor = led.getFill() == Color.RED ? Color.ANTIQUEWHITE : Color.RED;
                for (Circle c : incomingCallLeds) {
                    c.setFill(newColor);
                }
            } else {
                Circle led = incomingCallLeds.iterator().next();
                Color newColor = Color.ANTIQUEWHITE;
                for (Circle c : incomingCallLeds) {
                    c.setFill(newColor);
                }
            }
            Thread.sleep(500);
        }
    }

    public void handleNorthClicked(MouseEvent mouseEvent) {
        LOGGER.debug("North");
    }

    public void handleWestClicked(MouseEvent mouseEvent) {
        LOGGER.debug("West");
    }

    public void handleEastClicked(MouseEvent mouseEvent) {
        LOGGER.debug("East");
    }

    public void handleSouthClicked(MouseEvent mouseEvent) {
        LOGGER.debug("South");
    }

    public void stateChanged(ButtonState state) {
        LOGGER.debug("State changed {}", state);
        this.state = state;
    }


}
