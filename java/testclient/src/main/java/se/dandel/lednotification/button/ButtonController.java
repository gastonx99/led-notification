package se.dandel.lednotification.button;

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

    public Circle[] getCircles() {
        return new Circle[] {northnortheast, eastnortheast, east, eastsoutheast, southsoutheast, south, southsouthwest, westsouthwest, west, westnorthwest, northnorthwest, north};
    }

}
