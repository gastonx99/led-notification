package se.dandel.lednotification.button;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.lednotification.Event;
import se.dandel.lednotification.EventPriority;
import se.dandel.lednotification.EventSource;
import se.dandel.lednotification.EventType;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class Button extends Application {
    public static final int ALERT_BLINK_MILLIS = 1000;
    private static final long NOTIFICATION_BLINK_MILLIS = 2000;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private ButtonController controllerHandle;

    public static void main(String[] args) {
        launch(args);
    }

    private UdpAPI udp = new UdpAPI();

    private InternetButtonAPI b;

    private ExecutorService internetButtonExecutor;

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

        b = new InternetButtonAPI(controllerHandle.getCircles());
        setup();
        outerLoop();
    }

    private void setup() throws IOException {
        b.begin();
        udp.begin(5001);
        udp.joinMulticast(new UdpAPI.IPAddress("224.6.0.0"));
    }

    private void outerLoop() {
        internetButtonExecutor = Executors.newSingleThreadExecutor();
        internetButtonExecutor.submit(() -> {
                    try {
                        while (true) {
                            innerLoop();
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

    private final byte[] buffer = new byte[512];
    private final LedState ledState = new LedState();
    private long lastAlertBlinkTime = System.currentTimeMillis();
    private long lastNotificationBlinkTime = System.currentTimeMillis();


    private void innerLoop() throws Exception {
        int available = udp.parsePacket();
        if (available > 0) {
            udp.read(buffer, available);
            String eventStr = new String(buffer, 0, available, "UTF8");
            LOGGER.debug("Event received: " + eventStr);
            Event event = parseEvent(eventStr);
            LOGGER.debug("Event {}", event);
            updateLedState(event);
        }

        long currentTimeMillis = System.currentTimeMillis();
        if (lastAlertBlinkTime + ALERT_BLINK_MILLIS < currentTimeMillis) {
            ledState.setAlertOn(!ledState.isAlertOn());
            lastAlertBlinkTime = currentTimeMillis;
        }
        if (lastNotificationBlinkTime + NOTIFICATION_BLINK_MILLIS < currentTimeMillis) {
            ledState.setNotificationOn(!ledState.isNotificationOn());
            lastNotificationBlinkTime = currentTimeMillis;
        }

        if (!ledState.isAlert() && ledState.isAlertOn()) {
            ledState.setAlertOn(false);
            b.allLedsOff();
        }
        if (ledState.isAlert()) {
            handleAlert();
        } else {
            if (ledState.isPriorityNotification(EventPriority.CRITICAL)) {
                handleCriticalPriority();
            }
            if (ledState.isPriorityNotification(EventPriority.HIGH)) {
                handleHighPriority();
            }
            if (ledState.isPriorityNotification(EventPriority.MEDIUM)) {
                handleMediumPriority();
            }
            if (ledState.isPriorityNotification(EventPriority.LOW)) {
                handleLowPriority();
            }
        }

/*
        b.ledOn(6, 0, 0, 255);
        sleep(1000);
        b.ledOff(6);
        sleep(1000);
*/

/*
        if (b.buttonOn(2)) {
            b.ledOn(3, 255, 0, 0);
        } else {
            // And if the button's not on, then the LED should be off
            b.ledOff(3);
        }
*/
    }

    private void handleCriticalPriority() {
        if (ledState.isNotificationOn()) {
            b.ledOn(1, 255, 0, 0);
            b.ledOn(2, 255, 0, 0);
        } else {
            b.ledOff(1);
            b.ledOff(2);
        }
    }

    private void handleHighPriority() {
        if (ledState.isNotificationOn()) {
            b.ledOn(4, 255, 140, 0);
            b.ledOn(5, 255, 140, 0);
        } else {
            b.ledOff(1);
            b.ledOff(2);
        }
    }

    private void handleMediumPriority() {
        if (ledState.isNotificationOn()) {
            b.ledOn(7, 0, 0, 255);
            b.ledOn(8, 0, 0, 255);
        } else {
            b.ledOff(1);
            b.ledOff(2);
        }
    }

    private void handleLowPriority() {
        if (ledState.isNotificationOn()) {
            b.ledOn(10, 0, 255, 0);
            b.ledOn(11, 0, 255, 0);
        } else {
            b.ledOff(1);
            b.ledOff(2);
        }
    }

    private void handleAlert() {
        if (ledState.isAlertOn()) {
            b.allLedsOn(255, 0, 0);
        } else {
            b.allLedsOff();
        }
    }

    private void updateLedState(Event event) {
        switch (event.getType()) {
            case NEW_ALERT:
                ledState.setAlert(true);
                break;
            case CANCEL_ALERT:
                ledState.setAlert(false);
                break;
            case NEW_NOTIFICATION:
                ledState.increasePriorityNotifications(event.getPriority());
                break;
            case CANCEL_NOTIFICATION:
                ledState.decreasePriorityNotifications(event.getPriority());
                break;
            default:
                break;
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        LOGGER.debug("Shutting down initiated");
        internetButtonExecutor.shutdownNow();
        LOGGER.debug("Shutting down completed");
    }

    private Event parseEvent(String message) {
        String[] split = message.split(";");
        EventSource source = new EventSource(split[0].split("=")[1]);
        EventType type = EventType.valueOf(split[1].split("=")[1]);
        EventPriority priority = null;
        if(split.length > 2) {
            priority = EventPriority.valueOf(split[2].split("=")[1]);
        }
        return new Event(source, type, priority);
    }

}
