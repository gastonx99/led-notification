package se.dandel.lednotification.button;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import se.dandel.lednotification.EventPriority;

public class LedState {
    private boolean ping;
    private boolean alert;
    private boolean alertOn;
    private boolean notificationOn;
    private int[] priorityNotifications = new int[EventPriority.values().length];

    public void setPing(boolean ping) {
        this.ping = ping;
    }

    public boolean isPing() {
        return ping;
    }

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

    public boolean isNotification(EventPriority priority) {
        return priorityNotifications[priority.ordinal()] > 0;
    }

    public void increaseNotifications(EventPriority priority) {
        this.priorityNotifications[priority.ordinal()]++;
    }

    public void decreaseNotifications(EventPriority priority) {
        this.priorityNotifications[priority.ordinal()]--;
    }

    public boolean isAnyNotification() {
        for (int i = 0; i < priorityNotifications.length; i++) {
            if(priorityNotifications[i] > 0) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
