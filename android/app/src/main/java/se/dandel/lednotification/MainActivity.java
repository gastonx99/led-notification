package se.dandel.lednotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ToggleButton incomingCallButton;
    private Button acceptCallButton;
    private Button denyCallButton;
    private Button missedCallButton;
    private ToggleButton homeButton;
    private Button pingButton;
    private ToggleButton callNotificationButton;
    private ToggleButton messNotificationButton;
    private ToggleButton mailNotificationButton;
    private ToggleButton facebookNotificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        incomingCallButton = (ToggleButton) findViewById(R.id.incomingCallButton);
        acceptCallButton = (Button) findViewById(R.id.acceptCallButton);
        denyCallButton = (Button) findViewById(R.id.denyCallButton);
        missedCallButton = (Button) findViewById(R.id.missedCallButton);
        homeButton = (ToggleButton) findViewById(R.id.homeButton);
        pingButton = (Button) findViewById(R.id.pingButton);

        callNotificationButton = (ToggleButton) findViewById(R.id.callNotificationButton);
        messNotificationButton = (ToggleButton) findViewById(R.id.messNotificationButton);
        mailNotificationButton = (ToggleButton) findViewById(R.id.mailNotificationButton);
        facebookNotificationButton = (ToggleButton) findViewById(R.id.facebookNotificationButton);

        setCallButtonsState(false);
        pingButton.setEnabled(false);
    }

    public void callButtonClick(View view) {
        Log.d(TAG, "callButtonClick");
    }

    public void messButtonClick(View view) {
    }

    public void mailButtonClick(View view) {
    }

    public void facebookButtonClick(View view) {
    }

    public void homeButtonClick(View view) {
        pingButton.setEnabled(homeButton.isChecked());
    }

    public void pingButtonClick(View view) {
    }

    public void incomingCallButtonClick(View view) {
        Log.d(TAG, "incomingCallButtonClick");
        setCallButtonsState(true);
    }

    private void setCallButtonsState(boolean incomingCall) {
        incomingCallButton.setEnabled(!incomingCall);
        incomingCallButton.setChecked(incomingCall);
        acceptCallButton.setEnabled(incomingCall);
        denyCallButton.setEnabled(incomingCall);
        missedCallButton.setEnabled(incomingCall);
    }

    public void acceptCallButtonClick(View view) {
        setCallButtonsState(false);
    }

    public void missedCallButtonClick(View view) {
        setCallButtonsState(false);
        callNotificationButton.setChecked(true);
    }

    public void denyCallButtonClick(View view) {
        setCallButtonsState(false);
    }
}
