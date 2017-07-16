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
      b.ledOn(6, 0, 255, 0);
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
        b.ledOn(1, 255, 0, 0);
        b.ledOn(2, 255, 0, 0);
    }
    if (ledState.isNotification(lednotification::EventPriority::HIGH)) {
        b.ledOn(4, 255, 140, 0);
        b.ledOn(5, 255, 140, 0);
    }
    if (ledState.isNotification(lednotification::EventPriority::MEDIUM)) {
        b.ledOn(7, 255, 165, 0);
        b.ledOn(8, 255, 165, 0);
    }
    if (ledState.isNotification(lednotification::EventPriority::LOW)) {
        b.ledOn(10, 255, 255, 0);
        b.ledOn(11, 255, 255, 0);
    }
}

void handleAlertOn() {
    int red = 255;
    int green = 0;
    int blue = 0;
    b.ledOn(1, red, green, blue);
    b.ledOn(2, red, green, blue);
    b.ledOn(3, red, green, blue);
    b.ledOn(4, red, green, blue);
    b.ledOn(5, red, green, blue);
    b.ledOn(7, red, green, blue);
    b.ledOn(8, red, green, blue);
    b.ledOn(9, red, green, blue);
    b.ledOn(10, red, green, blue);
    b.ledOn(11, red, green, blue);
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
