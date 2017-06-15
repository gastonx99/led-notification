import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.lednotification.Notification;
import se.dandel.lednotification.NotificationSource;
import se.dandel.lednotification.NotificationType;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Button extends Application {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String GROUP_IPADDRESS = "224.6.0.0";
    private static final int PORT = 5001;
    private MulticastSocket socket;
    private ButtonController controllerHandle;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        startListening();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("button.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Internet Button simulator");
        stage.setScene(scene);
        stage.show();

        controllerHandle = (ButtonController) loader.getController();
        controllerHandle.initializeLeds();
    }

    private void startListening() {
        try {
            socket = new MulticastSocket(PORT);
            InetAddress group = InetAddress.getByName(GROUP_IPADDRESS);
            socket.joinGroup(group);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                listen();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }

    private void listen() throws Exception {
        byte[] buf = new byte[512];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        ButtonState state = new ButtonState();
        while (true) {
            LOGGER.info("Waiting for data");
            socket.receive(packet);
            String message = new String(buf,0, packet.getLength(), "UTF8");
            LOGGER.info("Data received: " + message);

            Notification notification = parseNotification(message);
            LOGGER.debug("Notification {}", notification);

            switch (notification.getNotificationType()) {
                case INCOMING_CALL:
                    state.setIncomingCall(true);
                    break;
                case MISSED_CALL:
                case DENIED_CALL:
                case ANSWERED_CALL:
                    state.setIncomingCall(false);
                    break;
                default:
                    break;
            }
            controllerHandle.stateChanged(state);
        }
    }

    private Notification parseNotification(String message) {
        String[] split = message.split(";");
        NotificationSource source = new NotificationSource(split[0].split("=")[1]);
        NotificationType type = NotificationType.valueOf(split[1].split("=")[1]);
        return new Notification(source, type);
    }

}
