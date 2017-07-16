#ifndef __LEDSTATE_H_INCLUDED__
#define __LEDSTATE_H_INCLUDED__

#include "Event.h"

#define NUM_PRIO_NOTIFICATIONS 4

namespace lednotification {

    class LedState {
        bool ping;
        bool alert;
        bool alertOn;
        bool notificationOn;
        int priorityNotifications [NUM_PRIO_NOTIFICATIONS] = { };

      public:
        bool isPing() { return ping; }
        bool isAlertOn() { return alertOn; }
        bool isNotificationOn() { return notificationOn; }
        bool isAlert() { return alert; }
        bool isNotification(EventPriority p) { return priorityNotifications[p] > 0; }
        bool isAnyNotification();

        void setPing(bool p) { ping = p; }
        void setAlertOn(bool on) { alertOn = on; }
        void setNotificationOn(bool on) { notificationOn = on; }
        void setAlert(bool a) { alert = a; }

        void update(Event* event);
        void increaseNotifications(EventPriority p) { priorityNotifications[p]++; }
        void decreaseNotifications(EventPriority p) { priorityNotifications[p]--; }
    };

}


#endif
