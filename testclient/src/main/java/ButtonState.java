import java.util.Map;

public class ButtonState {

    private boolean incomingCall;

    public boolean isIncomingCall() {
        return incomingCall;
    }

    public void setIncomingCall(boolean incomingCall) {
        this.incomingCall = incomingCall;
    }

    @Override
    public String toString() {
        return "ButtonState{" +
                "incomingCall=" + incomingCall +
                '}';
    }
}
