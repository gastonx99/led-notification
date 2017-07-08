#include "LedState.h"

bool LedState::isAnyNotification() {
    for (int i = 0; i < NUM_PRIO_NOTIFICATIONS; i++) {
        if(priorityNotifications[i] > 0) {
            return true;
        }
    }
    return false;
}
