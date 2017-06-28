package se.dandel.lednotification;

public interface AlertSender {

    void send(Event event);

    void startReading(Callback callback);

    void stopReading();

    public interface Callback {
        void exeute(Event event);
    }

}
