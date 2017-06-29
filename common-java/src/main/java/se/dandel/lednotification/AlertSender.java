package se.dandel.lednotification;

public interface AlertSender {

    void send(Event event);

    void startReading();

    void stopReading();
}
