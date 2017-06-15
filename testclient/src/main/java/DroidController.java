import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Module;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import se.dandel.lednotification.BroadcastingNotificationSender;
import se.dandel.lednotification.NotificationSender;
import se.dandel.lednotification.NotificationService;
import se.dandel.lednotification.NotificationSource;

public class DroidController {
    @FXML
    private VBox rootPane;
    @FXML
    private Button comingHomeButton;
    @FXML
    private Button leavingHomeButton;
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

    private NotificationService service;
    private Circle circleLed1;
    private NotificationSource source = new NotificationSource("DROID TEST CLIENT");

    public DroidController() {
        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bind(NotificationSender.class).to(BroadcastingNotificationSender.class);
            }
        };
        this.service = Guice.createInjector(module).getInstance(NotificationService.class);
    }

    @FXML
    void initialize() {
        rootPane.requestFocus();
        addLedIndicators();
        inactivateLeds();
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
    }

    public void handleFacebookNotificationAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        if (facebookNotificationButton.isSelected()) {
            service.newNotification(source);
        } else {
            service.dismissNotification(source);
        }
    }

    public void handleCallNotificationAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        if (callNotificationButton.isSelected()) {
            service.newNotification(source);
        } else {
            service.dismissNotification(source);
        }
    }

    public void handleMailNotificationAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        if (mailNotificationButton.isSelected()) {
            service.newNotification(source);
        } else {
            service.dismissNotification(source);
        }
    }

    public void handleMessNotificationAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        if (messNotificationButton.isSelected()) {
            service.newNotification(source);
        } else {
            service.dismissNotification(source);
        }
    }

    public void handleComingHomeAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        comingHomeButton.disableProperty().set(true);
        leavingHomeButton.disableProperty().set(false);
        activateLeds();
        service.enteringHome(source);
    }

    public void handleLeavingHomeAction(ActionEvent actionEvent) {
        rootPane.requestFocus();
        comingHomeButton.disableProperty().set(false);
        leavingHomeButton.disableProperty().set(true);
        inactivateLeds();
        service.leavingHome(source);
    }

    private void inactivateLeds() {
        circleLed1.setFill(Color.DARKGRAY);
    }

    private void activateLeds() {
        circleLed1.setFill(Color.GREEN);
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

}
