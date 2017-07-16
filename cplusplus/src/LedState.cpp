#include "spark_wiring_usbserial.h"
#include "LedState.h"
#include "MyEvent.h"

namespace lednotification {

    void LedState::update(MyEvent* event) {
      switch (event->getType()) {
          case MyEventType::NEW_ALERT:
              setAlert(true);
              break;
          case MyEventType::CANCEL_ALERT:
              setAlert(false);
              break;
          case MyEventType::NEW_NOTIFICATION:
              increaseNotifications(event->getPriority());
              Serial.println("Increase notification with priority");
              Serial.println(event->getPriority());
              break;
          case MyEventType::CANCEL_NOTIFICATION:
              decreaseNotifications(event->getPriority());
              Serial.println("Decrease notification with priority");
              Serial.println(event->getPriority());
              break;
          default:
              break;
      }
    }


    bool LedState::isAnyNotification() {
        for (int i = 0; i < NUM_PRIO_NOTIFICATIONS; i++) {
            if(priorityNotifications[i] > 0) {
                return true;
            }
        }
        return false;
    }
}
