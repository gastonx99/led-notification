package se.dandel.lednotification.button;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class LedState {
    private boolean incomingCallOn;
    private boolean notificationOn;

    private boolean incomingCall;
    private int criticalPriorityNotifications;
    private int highPriorityNotifications;
    private int mediumPriorityNotifications;
    private int lowPriorityNotifications;

    public void setIncomingCallOn(boolean incomingCallOn) {
        this.incomingCallOn = incomingCallOn;
    }

    public boolean isIncomingCallOn() {
        return incomingCallOn;
    }

    public void setNotificationOn(boolean notificationOn) {
        this.notificationOn = notificationOn;
    }

    public boolean isNotificationOn() {
        return notificationOn;
    }

    public boolean isIncomingCall() {
        return incomingCall;
    }

    public void setIncomingCall(boolean incomingCall) {
        this.incomingCall = incomingCall;
    }

    public boolean isCriticalPriorityNotification() {
        return criticalPriorityNotifications > 0;
    }

    public void increaseCriticalPriorityNotifications() {
        this.criticalPriorityNotifications++;
    }

    public void decreaseCriticalPriorityNotifications() {
        this.criticalPriorityNotifications--;
    }

    public boolean isHighPriorityNotification() {
        return highPriorityNotifications > 0;
    }

    public void increaseHighPriorityNotifications() {
        this.highPriorityNotifications++;
    }

    public void decreaseHighPriorityNotifications() {
        this.highPriorityNotifications--;
    }

    public boolean isMediumPriorityNotification() {
        return mediumPriorityNotifications > 0;
    }

    public void increaseMediumPriorityNotifications() {
        this.mediumPriorityNotifications++;
    }

    public void decreaseMediumPriorityNotifications() {
        this.mediumPriorityNotifications--;
    }

    public boolean isLowPriorityNotification() {
        return lowPriorityNotifications > 0;
    }

    public void increaseLowPriorityNotifications() {
        this.lowPriorityNotifications++;
    }

    public void decreaseLowPriorityNotifications() {
        this.lowPriorityNotifications--;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
