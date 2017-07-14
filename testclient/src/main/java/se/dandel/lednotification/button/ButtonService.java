package se.dandel.lednotification.button;

import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.lednotification.Event;
import se.dandel.lednotification.EventPriority;
import se.dandel.lednotification.EventSource;
import se.dandel.lednotification.EventType;

import java.io.IOException;

public class ButtonService {
    private static final int ALERT_BLINK_MILLIS = 1000;
    private static final long NOTIFICATION_BLINK_MILLIS = 2000;
    private static final long PING_MILLIS = 20000;
    private static final int RECEIVING_PORT = 5001;
    private static final int SENDING_PORT = 5002;
    private static final String ADDRESS = "239.255.0.0";

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private UdpAPI udp = new UdpAPI();

    private InternetButtonAPI b;

    public ButtonService(Circle[] circles) {
        b = new InternetButtonAPI(circles);
    }

    public void setup() throws IOException {
        b.begin();
        udp.begin(RECEIVING_PORT);
        udp.joinMulticast(new UdpAPI.IPAddress(ADDRESS));
    }

    private final byte[] buffer = new byte[512];
    private final LedState ledState = new LedState();
    private long lastAlertBlinkTime = System.currentTimeMillis();
    private long lastNotificationBlinkTime = System.currentTimeMillis();
    private long lastSourceActivityTime = 0;


    public void innerLoop() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();

        Event event = readEvent();
        if(event != null) {
            lastSourceActivityTime = currentTimeMillis;
            if(EventType.PING == event.getType()) {
                sendPong();
            }
            updateLedState(event);
        }

        ledState.setPing(lastSourceActivityTime + PING_MILLIS > currentTimeMillis);

        if (lastAlertBlinkTime + ALERT_BLINK_MILLIS < currentTimeMillis) {
            ledState.setAlertOn(!ledState.isAlertOn());
            lastAlertBlinkTime = currentTimeMillis;
        }
        if (lastNotificationBlinkTime + NOTIFICATION_BLINK_MILLIS < currentTimeMillis) {
            ledState.setNotificationOn(!ledState.isNotificationOn());
            lastNotificationBlinkTime = currentTimeMillis;
        }
        if (!ledState.isAlert()) {
            ledState.setAlertOn(false);
        }
        if (!ledState.isAnyNotification()) {
            ledState.setNotificationOn(false);
        }

        handleLeds();
    }

    private void handleLeds() {
        if(ledState.isPing()) {
            b.ledOn(6, 0, 255, 0);
        } else {
            b.ledOff(6);
        }

        if(ledState.isAlert()) {
            if (ledState.isAlertOn()) {
                handleAlertOn();
            } else {
                b.ledOff(1);
                b.ledOff(2);
                b.ledOff(3);
                b.ledOff(4);
                b.ledOff(5);
                b.ledOff(7);
                b.ledOff(8);
                b.ledOff(9);
                b.ledOff(10);
                b.ledOff(11);
            }
        } else {
            if (ledState.isNotificationOn()) {
                handleNotificationOn();
            } else {
                b.ledOff(1);
                b.ledOff(2);
                b.ledOff(3);
                b.ledOff(4);
                b.ledOff(5);
                b.ledOff(7);
                b.ledOff(8);
                b.ledOff(9);
                b.ledOff(10);
                b.ledOff(11);
            }
        }
    }

    private void handleNotificationOn() {
        if (ledState.isNotification(EventPriority.CRITICAL)) {
            b.ledOn(1, 255, 0, 0);
            b.ledOn(2, 255, 0, 0);
        }
        if (ledState.isNotification(EventPriority.HIGH)) {
            b.ledOn(4, 255, 140, 0);
            b.ledOn(5, 255, 140, 0);
        }
        if (ledState.isNotification(EventPriority.MEDIUM)) {
            b.ledOn(7, 255, 165, 0);
            b.ledOn(8, 255, 165, 0);
        }
        if (ledState.isNotification(EventPriority.LOW)) {
            b.ledOn(10, 255, 255, 0);
            b.ledOn(11, 255, 255, 0);
        }
    }

    private void handleAlertOn() {
        int red = 255;
        int green = 0;
        int blue = 0;
        b.ledOn(1, red, green, blue);
        b.ledOn(2, red, green, blue);
        b.ledOn(3, red, green, blue);
        b.ledOn(4, red, green, blue);
        b.ledOn(5, red, green, blue);
        b.ledOn(7, red, green, blue);
        b.ledOn(8, red, green, blue);
        b.ledOn(9, red, green, blue);
        b.ledOn(10, red, green, blue);
        b.ledOn(11, red, green, blue);
    }

    private void sendPong() {
        LOGGER.debug("Sending pong");
        udp.write(SENDING_PORT, "source=BUTTON TEST CLIENT;type=PONG");
    }

    private Event readEvent() throws IOException {
        Event event = null;
        int available = udp.parsePacket();
        if (available > 0) {
            udp.read(buffer, available);
            String eventStr = new String(buffer, 0, available, "UTF8");
            LOGGER.debug("Event received: " + eventStr);
            event = parseEvent(eventStr);
            LOGGER.debug("Event {}", event);
        }
        return event;
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

    private void updateLedState(Event event) {
        switch (event.getType()) {
            case NEW_ALERT:
                ledState.setAlert(true);
                break;
            case CANCEL_ALERT:
                ledState.setAlert(false);
                break;
            case NEW_NOTIFICATION:
                ledState.increaseNotifications(event.getPriority());
                break;
            case CANCEL_NOTIFICATION:
                ledState.decreaseNotifications(event.getPriority());
                break;
            default:
                break;
        }
    }

}
