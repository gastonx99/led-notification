package se.dandel.lednotification;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Event {

    private EventType type;

    private EventSource source;

    private EventPriority priority;

    public Event(EventSource source, EventType type) {
        this.source = source;
        this.type = type;
    }

    public Event(EventSource source, EventType type, EventPriority priority) {
        this(source, type);
        this.priority = priority;
    }

    public EventSource getSource() {
        return source;
    }

    public EventType getType() {
        return type;
    }

    public void setSource(EventSource source) {
        this.source = source;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public void setPriority(EventPriority priority) {
        this.priority = priority;
    }

    public static Event newAlert(EventSource source) {
        return new Event(source, EventType.NEW_ALERT);
    }

    public static Event cancelAlert(EventSource source) {
        return new Event(source, EventType.CANCEL_ALERT);
    }

    public static Event newNotification(EventSource source, EventPriority priority) {
        return new Event(source, EventType.NEW_NOTIFICATION, priority);
    }

    public static Event cancelNotification(EventSource source, EventPriority priority) {
        return new Event(source, EventType.CANCEL_NOTIFICATION, priority);
    }

    public static Event ping(EventSource source) {
        return new Event(source, EventType.PING);
    }

    public static Event pong(EventSource source) {
        return new Event(source, EventType.PONG);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
