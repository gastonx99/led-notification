package se.dandel.lednotification;

public class Notification {

    private NotificationType notificationType;

    private NotificationSource source;

    public Notification(NotificationSource source, NotificationType type) {
        this.source = source;
        this.notificationType = type;
    }

    public NotificationSource getSource() {
        return source;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setSource(NotificationSource source) {
        this.source = source;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public static Notification incomingCall(NotificationSource source) {
        return new Notification(source, NotificationType.INCOMING_CALL);
    }

    public static Notification answeredCall(NotificationSource source) {
        return new Notification(source, NotificationType.ANSWERED_CALL);
    }
    public static Notification deniedCall(NotificationSource source) {
        return new Notification(source, NotificationType.DENIED_CALL);
    }

    public static Notification missedCall(NotificationSource source) {
        return new Notification(source, NotificationType.MISSED_CALL);
    }

    public static Notification newNotification(NotificationSource source) {
        return new Notification(source, NotificationType.NEW_NOTIFICATION);
    }

    public static Notification dismissedNotification(NotificationSource source) {
        return new Notification(source, NotificationType.DISMISSED_NOTIFICATION);
    }

    public static Notification enteringHome(NotificationSource source) {
        return new Notification(source, NotificationType.ENTERING_HOME);
    }

    public static Notification leavingHome(NotificationSource source) {
        return new Notification(source, NotificationType.LEAVING_HOME);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationType=" + notificationType +
                ", source=" + source +
                '}';
    }

}
