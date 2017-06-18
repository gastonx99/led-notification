package se.dandel.lednotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class SmartPhoneAlertService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private AlertSender alertSender;

    public void incomingCall(EventSource source) {
        Event event = Event.newAlert(source);
        LOGGER.debug("Incoming call {}", event);
        alertSender.send(event);
    }

    public void callAnswered(EventSource source) {
        Event event = Event.cancelAlert(source);
        LOGGER.debug("Answered call {}", event);
        alertSender.send(event);
    }

    public void callDenied(EventSource source) {
        Event event = Event.cancelAlert(source);
        LOGGER.debug("Denied call {}", event);
        alertSender.send(event);
    }

    public void callMissed(EventSource source) {
        Event event = Event.cancelAlert(source);
        LOGGER.debug("Missed call {}", event);
        alertSender.send(event);
    }

    public void newNotification(EventSource source, EventPriority priority) {
        Event event = Event.newNotification(source, priority);
        LOGGER.debug("Show event {}", event);
        alertSender.send(event);
    }

    public void dismissNotification(EventSource source, EventPriority priority) {
        Event event = Event.cancelNotification(source, priority);
        LOGGER.debug("Dismiss event {}", event);
        alertSender.send(event);
    }

    public void enteringWifi(EventSource source) {
        Event event = Event.ping(source);
        LOGGER.debug("Entering home {}", event);
        alertSender.send(event);
    }

    public void leavingWifi(EventSource source) {
        Event event = Event.pong(source);
        LOGGER.debug("Leaving home {}", event);
        alertSender.send(event);
    }

}
