package se.dandel.lednotification.button;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import se.dandel.lednotification.EventPriority;

public class LedState {
    private boolean alertOn;
    private boolean notificationOn;

    private boolean alert;

    private int[] priorityNotifications = new int[EventPriority.values().length];

    public void setAlertOn(boolean alertOn) {
        this.alertOn = alertOn;
    }

    public boolean isAlertOn() {
        return alertOn;
    }

    public void setNotificationOn(boolean notificationOn) {
        this.notificationOn = notificationOn;
    }

    public boolean isNotificationOn() {
        return notificationOn;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public boolean isPriorityNotification(EventPriority priority) {
        return priorityNotifications[priority.ordinal()] > 0;
    }

    public void increasePriorityNotifications(EventPriority priority) {
        this.priorityNotifications[priority.ordinal()]++;
    }

    public void decreasePriorityNotifications(EventPriority priority) {
        this.priorityNotifications[priority.ordinal()]--;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
