package se.dandel.lednotification.button;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class InternetButtonAPI {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public InternetButtonAPI(Circle[] circles) {
        leds.addAll(createLeds(circles));
        buttons.addAll(createButtons(circles[11], circles[2], circles[5], circles[8]));
    }

    private List<Button> createButtons(Circle... circle) {
        List<Button> buttons = new ArrayList<>();
        for (Circle c : circle) {
            buttons.add(new Button(c));
        }
        return buttons;
    }

    private List<Led> createLeds(Circle[] circles) {
        List<Led> l = new ArrayList<>();
        for (int i1 = 0; i1 < circles.length - 1; i1++) {
            l.add(new Led(circles[i1]));
        }
        return l;
    }

    public class Led {
        Circle circle;

        public Led(Circle circle) {
            this.circle = circle;
        }
    }

    public class Button {
        Circle circle;
        boolean on;

        public Button(Circle circle) {
            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
                LOGGER.debug("Mouse clicked");
                if (me.getButton().equals(MouseButton.PRIMARY)) {
                    on = !on;
                }
            });
        }
    }

    private final List<Led> leds = new ArrayList<>();
    private final List<Button> buttons = new ArrayList<>();


    private boolean ready;

    public void begin() {
        // Do nothing, just here coz it is in the real API
        ready = true;
    }

    public void begin(int version) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setNumLeds(int num) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void allLedsOff() {
        leds.stream().forEach(b -> b.circle.setFill(Color.TRANSPARENT));
    }

    public void allLedsOn(int red, int green, int blue) {
        leds.stream().forEach(b -> b.circle.setFill(Color.rgb(red, green, blue)));
    }

    public void ledOn(int led, int red, int green, int blue) {
        Validate.isTrue(led >= 1 && led <= leds.size());
        leds.get(led - 1).circle.setFill(Color.rgb(red, green, blue));
    }

    public void ledOff(int led) {
        Validate.isTrue(led >= 1 && led <= leds.size());
        leds.get(led - 1).circle.setFill(Color.TRANSPARENT);
    }

    public void rainbow(int wait) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void advanceRainbow(int amount, int wait) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void playNote(String note, int duration) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void playSong(String song) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void smoothLedOn(float i, int red, int green, int blue) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setBrightness(int brightness) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setBPM(int beatsPerMinute) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void clickButton(int button) {
        Validate.isTrue(button >= 1 && button <= buttons.size());
        buttons.get(button - 1).on = !buttons.get(button - 1).on;
    }

    public boolean buttonOn(int button) {
        Validate.isTrue(button >= 1 && button <= buttons.size());
        return buttons.get(button - 1).on;
    }

    public boolean allButtonsOn() {
        return buttons.stream().allMatch(button -> button.on);
    }

    public boolean allButtonsOff() {
        return buttons.stream().allMatch(button -> !button.on);
    }

    public int lowestLed() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getBrightness() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int readX() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int readY() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int readZ() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int readX16() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int readY16() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int readZ16() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getBPM() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int wheel(int wheelPos) {
        throw new UnsupportedOperationException("Not implemented yet");
    }


}
