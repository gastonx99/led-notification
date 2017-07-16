#include "spark_wiring_usbserial.h"
#include "LedState.h"
#include "Event.h"

namespace lednotification {

    void LedState::update(Event* event) {
      switch (event->getType()) {
          case EventType::NEW_ALERT:
              setAlert(true);
              break;
          case EventType::CANCEL_ALERT:
              setAlert(false);
              break;
          case EventType::NEW_NOTIFICATION:
              increaseNotifications(event->getPriority());
              Serial.println("Increase notification with priority");
              Serial.println(event->getPriority());
              break;
          case EventType::CANCEL_NOTIFICATION:
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
