package se.dandel.lednotification.droid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import se.dandel.lednotification.EventBus;
import se.dandel.lednotification.EventPriority;
import se.dandel.lednotification.EventSource;
import se.dandel.lednotification.SmartPhoneAlertService;

import javax.inject.Inject;

public class DroidController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @FXML
    private VBox rootPane;
    @FXML
    private ToggleButton enteringWifiButton;
    @FXML
    private Button pingButton;
    @FXML
    private ToggleButton callNotificationButton;
    @FXML
    private ToggleButton mailNotificationButton;
    @FXML
    private ToggleButton messNotificationButton;
    @FXML
    private ToggleButton facebookNotificationButton;
    @FXML
    private HBox ledIndicatorBox;
    @FXML
    private Button incomingCallButton;
    @FXML
    private Button acceptCallButton;
    @FXML
    private Button denyCallButton;
    @FXML
    private Button missedCallButton;

    @Inject
    private SmartPhoneAlertService service;

    @Inject
    private EventBus eventBus;

    private Circle circleLed1;
    private EventSource source = new EventSource("DROID TEST CLIENT");

    public DroidController() {
    }

    @FXML
    void initialize() {
        rootPane.requestFocus();
        addLedIndicators();
        inactivateLeds();
        eventBus.getEvents().subscribe(event1 -> {
            LOGGER.debug("Observed event {}", event1);
            activateLeds();
        });
    }

    public void handleIncomingCallAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        setDisabledPropertyOnCallButtons(true);
        service.incomingCall(source);
    }

    public void handleAcceptCallAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        setDisabledPropertyOnCallButtons(false);
        service.callAnswered(source);
    }

    public void handleDenyCallAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        setDisabledPropertyOnCallButtons(false);
        service.callDenied(source);
    }

    public void handleMissedCallAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        setDisabledPropertyOnCallButtons(false);
        service.callMissed(source);
        missedCallButton.setCancelButton(false);
        callNotificationButton.setSelected(true);
        service.newNotification(source, EventPriority.CRITICAL);
    }

    public void handleFacebookNotificationAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        if (facebookNotificationButton.isSelected()) {
            service.newNotification(source, EventPriority.LOW);
        } else {
            service.dismissNotification(source, EventPriority.LOW);
        }
    }

    public void handleCallNotificationAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        if (callNotificationButton.isSelected()) {
            service.newNotification(source, EventPriority.CRITICAL);
        } else {
            service.dismissNotification(source, EventPriority.CRITICAL);
        }
    }

    public void handleMailNotificationAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        if (mailNotificationButton.isSelected()) {
            service.newNotification(source, EventPriority.MEDIUM);
        } else {
            service.dismissNotification(source, EventPriority.MEDIUM);
        }
    }

    public void handleMessNotificationAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        if (messNotificationButton.isSelected()) {
            service.newNotification(source, EventPriority.HIGH);
        } else {
            service.dismissNotification(source, EventPriority.HIGH);
        }
    }

    public void handleEnteringWifiAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        pingButton.disableProperty().set(!enteringWifiButton.isSelected());
        if (enteringWifiButton.isSelected()) {
            pendingLeds();
        } else {
            inactivateLeds();
        }
        service.enteringWifi(source);
    }

    public void handlePingAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        pendingLeds();
        service.ping(source);
    }

    private void activateLeds() {
        circleLed1.setFill(Color.GREEN);
    }

    private void inactivateLeds() {
        circleLed1.setFill(Color.DARKGRAY);
    }

    private void pendingLeds() {
        circleLed1.setFill(Color.ORANGE);
    }

    private void setDisabledPropertyOnCallButtons(boolean disableIncomingCall) {
        incomingCallButton.disableProperty().set(disableIncomingCall);
        acceptCallButton.disableProperty().set(!disableIncomingCall);
        denyCallButton.disableProperty().set(!disableIncomingCall);
        missedCallButton.disableProperty().set(!disableIncomingCall);
    }

    private void addLedIndicators() {
        circleLed1 = new Circle(10.0);
        ledIndicatorBox.getChildren().add(circleLed1);
    }

    public void shutdown() {
        service.shutdown();
    }
}
