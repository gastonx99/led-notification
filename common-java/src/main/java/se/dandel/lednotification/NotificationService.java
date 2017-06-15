package se.dandel.lednotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class NotificationService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private NotificationSender notificationSender;

    public void incomingCall(NotificationSource source) {
        Notification notification = Notification.incomingCall(source);
        LOGGER.debug("Incoming call {}", notification);
        notificationSender.send(notification);
    }

    public void callAnswered(NotificationSource source) {
        Notification notification = Notification.answeredCall(source);
        LOGGER.debug("Answered call {}", notification);
        notificationSender.send(notification);
    }

    public void callDenied(NotificationSource source) {
        Notification notification = Notification.deniedCall(source);
        LOGGER.debug("Denied call {}", notification);
        notificationSender.send(notification);
    }

    public void callMissed(NotificationSource source) {
        Notification notification = Notification.missedCall(source);
        LOGGER.debug("Missed call {}", notification);
        notificationSender.send(notification);
    }

    public void newNotification(NotificationSource source) {
        Notification notification = Notification.newNotification(source);
        LOGGER.debug("Show notification {}", notification);
        notificationSender.send(notification);
    }

    public void dismissNotification(NotificationSource source) {
        Notification notification = Notification.dismissedNotification(source);
        LOGGER.debug("Dismiss notification {}", notification);
        notificationSender.send(notification);
    }

    public void enteringHome(NotificationSource source) {
        Notification notification = Notification.enteringHome(source);
        LOGGER.debug("Entering home {}", notification);
        notificationSender.send(notification);
    }

    public void leavingHome(NotificationSource source) {
        Notification notification = Notification.leavingHome(source);
        LOGGER.debug("Leaving home {}", notification);
        notificationSender.send(notification);
    }

}
