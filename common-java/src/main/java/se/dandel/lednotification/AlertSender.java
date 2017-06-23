package se.dandel.lednotification;

public interface AlertSender {

    void send(Event event);

    Event read(int timeout);

}
