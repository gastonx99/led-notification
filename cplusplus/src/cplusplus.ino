// This #include statement was automatically added by the Particle IDE.
#include <InternetButton.h>
#include "LedState.h"
#include "EventService.h"
#include "Event.h"

using namespace std;

#define ALERT_BLINK_MILLIS 1000
#define NOTIFICATION_BLINK_MILLIS 2000
#define PING_MILLIS 20000
#define RECEIVING_PORT 5001
#define SENDING_PORT 5002
#define ADDRESS "239.255.0.0"

#define GREEN       0,255,0
#define RED         255,0,0
#define ORANGE      255,140,0
#define YELLOW      255,255,0
#define SALMON      250,128,114
#define CHOCOLATE   210,105,30
#define LAWN_GREEN  124,252,0
#define SANDY_BROWN 244,164,96
#define TURQUOISE   64,224,208

// Funkar ej
#define SKYBLUE     135,206,235
#define LIGHTGREEN  144,238,144
#define VIOLET      238,130,238
#define KHAKI       240,230,140
#define PALE_GREEN  152,251,152

#define CRITICAL_COLOR SALMON
#define HIGH_COLOR     CHOCOLATE
#define MEDIUM_COLOR   TURQUOISE
#define LOW_COLOR      SANDY_BROWN

#define PING_COLOR LAWN_GREEN
#define ALERT_COLOR SALMON

InternetButton b = InternetButton();
lednotification::EventService eventService = lednotification::EventService();
lednotification::LedState ledState = lednotification::LedState();

long long lastSourceActivityTime = 0;
long long lastAlertBlinkTime = 0;
long long lastNotificationBlinkTime = 0;

void setup() {
    // Tell b to get everything ready to go
    // Use b.begin(1); if you have the original SparkButton, which does not have a buzzer or a plastic enclosure
    // to use, just add a '1' between the parentheses in the code below.
    b.begin();
    eventService.initialize(RECEIVING_PORT);
}

void handleLeds() {
  if(ledState.isPing()) {
      b.ledOn(6, PING_COLOR);
  } else {
      b.ledOff(6);
  }

  if(ledState.isAlert()) {
      if (ledState.isAlertOn()) {
          handleAlertOn();
      } else {
          b.ledOff(1);
          b.ledOff(2);
          b.ledOff(3);
          b.ledOff(4);
          b.ledOff(5);
          b.ledOff(7);
          b.ledOff(8);
          b.ledOff(9);
          b.ledOff(10);
          b.ledOff(11);
      }
  } else {
      if (ledState.isNotificationOn()) {
          handleNotificationOn();
      } else {
          b.ledOff(1);
          b.ledOff(2);
          b.ledOff(3);
          b.ledOff(4);
          b.ledOff(5);
          b.ledOff(7);
          b.ledOff(8);
          b.ledOff(9);
          b.ledOff(10);
          b.ledOff(11);
      }
  }
}

void handleNotificationOn() {
    if (ledState.isNotification(lednotification::EventPriority::CRITICAL)) {
        b.ledOn(1, CRITICAL_COLOR);
        b.ledOn(2, CRITICAL_COLOR);
    }
    if (ledState.isNotification(lednotification::EventPriority::HIGH)) {
        b.ledOn(4, HIGH_COLOR);
        b.ledOn(5, HIGH_COLOR);
    }
    if (ledState.isNotification(lednotification::EventPriority::MEDIUM)) {
        b.ledOn(7, MEDIUM_COLOR);
        b.ledOn(8, MEDIUM_COLOR);
    }
    if (ledState.isNotification(lednotification::EventPriority::LOW)) {
        b.ledOn(10, LOW_COLOR);
        b.ledOn(11, LOW_COLOR);
    }
}

void handleAlertOn() {
    b.ledOn(1, ALERT_COLOR);
    b.ledOn(2, ALERT_COLOR);
    b.ledOn(3, ALERT_COLOR);
    b.ledOn(4, ALERT_COLOR);
    b.ledOn(5, ALERT_COLOR);
    b.ledOn(7, ALERT_COLOR);
    b.ledOn(8, ALERT_COLOR);
    b.ledOn(9, ALERT_COLOR);
    b.ledOn(10, ALERT_COLOR);
    b.ledOn(11, ALERT_COLOR);
}

void loop(){
  long long currentTimeMillis = millis();

  lednotification::Event* event = eventService.readEvent();
  if(event != NULL) {
      lastSourceActivityTime = currentTimeMillis;
      if(lednotification::EventType::PING == event->getType()) {
//          eventService.sendPong();
      }
      ledState.update(event);
  }

  ledState.setPing(lastSourceActivityTime + PING_MILLIS > currentTimeMillis);

  if (lastAlertBlinkTime + ALERT_BLINK_MILLIS < currentTimeMillis) {
      ledState.setAlertOn(!ledState.isAlertOn());
      lastAlertBlinkTime = currentTimeMillis;
  }
  if (lastNotificationBlinkTime + NOTIFICATION_BLINK_MILLIS < currentTimeMillis) {
      ledState.setNotificationOn(!ledState.isNotificationOn());
      lastNotificationBlinkTime = currentTimeMillis;
  }
  if (!ledState.isAlert()) {
      ledState.setAlertOn(false);
  }
  if (!ledState.isAnyNotification()) {
      ledState.setNotificationOn(false);
  }

  handleLeds();



//    b.allLedsOn(10,0,0);
//    delay(1000);
//    b.allLedsOff();
//    delay(1000);

    // Notice that I made them much dimmer, so it's a bit less painful
}
