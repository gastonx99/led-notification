package se.dandel.lednotification;

public class NotificationSource {
    private String source;

    public NotificationSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "NotificationSource{" +
                "source='" + source + '\'' +
                '}';
    }
}
