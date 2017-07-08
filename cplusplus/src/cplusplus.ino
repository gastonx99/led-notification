// This #include statement was automatically added by the Particle IDE.
#include <InternetButton.h>
#include "MyEvent.h"
#include "LedState.h"

using namespace std;

#define EVENT_BUFFER_SIZE 512
#define ALERT_BLINK_MILLIS 1000
#define NOTIFICATION_BLINK_MILLIS 2000
#define PING_MILLIS 20000
#define RECEIVING_PORT 5001
#define SENDING_PORT 5002
#define ADDRESS "224.0.0.1"

InternetButton b = InternetButton();
UDP udp;
LedState ledState = LedState();

long long lastSourceActivityTime = 0;
long long lastAlertBlinkTime = 0;
long long lastNotificationBlinkTime = 0;

void setup() {
    // Tell b to get everything ready to go
    // Use b.begin(1); if you have the original SparkButton, which does not have a buzzer or a plastic enclosure
    // to use, just add a '1' between the parentheses in the code below.
    b.begin();
    udp.begin(RECEIVING_PORT);
    Serial.begin(9600);
    Serial.println(WiFi.localIP());
    IPAddress multicastAddress(224,0,0,1);
    udp.joinMulticast(multicastAddress);
}

void loop(){
  long long currentTimeMillis = millis();

  MyEvent* event = readEvent();
  if(event != NULL) {
      lastSourceActivityTime = currentTimeMillis;
      if(MyEventType::PING == event->getType()) {
//          sendPong();
      }
      updateLedState(event);
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

void updateLedState(MyEvent* event) {
  switch (event->getType()) {
      case NEW_ALERT:
          ledState.setAlert(true);
          break;
      case CANCEL_ALERT:
          ledState.setAlert(false);
          break;
      case NEW_NOTIFICATION:
          ledState.increaseNotifications(event->getPriority());
          break;
      case CANCEL_NOTIFICATION:
          ledState.decreaseNotifications(event->getPriority());
          break;
      default:
          break;
  }
}

char buffer [EVENT_BUFFER_SIZE] = {};
MyEvent* readEvent() {
  MyEvent* event = NULL;
  int available = udp.parsePacket();
  if (available > 0) {
    udp.read(buffer, available);
    buffer[available] = 0;
    Serial.println("Received event");
    Serial.println(buffer);
    event = parseEvent(buffer);
    Serial.printlnf("Parsed event %s", event->getType());
  }
  return event;
}

MyEvent* parseEvent(char* message) {
  char* sourcePart = strtok(message, ";");
  char* typePart = strtok(NULL, ";");
  char* priorityPart = strtok(NULL, ";");
  char* source = strtok(NULL, strtok(sourcePart, "="));
  char* type = strtok(NULL, strtok(typePart, "="));
  char* priority = priorityPart == NULL ? NULL : strtok(NULL, strtok(priorityPart, "="));
  if(priority == NULL) {
    return new MyEvent(source, type);
  } else {
    return new MyEvent(source, type, priority);
  }
 }

void sendPong() {
  udp.write("source=BUTTON TEST CLIENT;type=PONG");
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
    if (ledState.isNotification(MyEventPriority::XCRITICAL)) {
        b.ledOn(1, 255, 0, 0);
        b.ledOn(2, 255, 0, 0);
    }
    if (ledState.isNotification(MyEventPriority::XHIGH)) {
        b.ledOn(4, 255, 140, 0);
        b.ledOn(5, 255, 140, 0);
    }
    if (ledState.isNotification(MyEventPriority::XMEDIUM)) {
        b.ledOn(7, 255, 165, 0);
        b.ledOn(8, 255, 165, 0);
    }
    if (ledState.isNotification(MyEventPriority::XLOW)) {
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
