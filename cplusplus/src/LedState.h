#ifndef __LEDSTATE_H_INCLUDED__
#define __LEDSTATE_H_INCLUDED__

#include "MyEvent.h"

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
        bool isNotification(MyEventPriority p) { return priorityNotifications[p] > 0; }
        bool isAnyNotification();

        void setPing(bool p) { ping = p; }
        void setAlertOn(bool on) { alertOn = on; }
        void setNotificationOn(bool on) { notificationOn = on; }
        void setAlert(bool a) { alert = a; }

        void update(MyEvent* event);
        void increaseNotifications(MyEventPriority p) { priorityNotifications[p]++; }
        void decreaseNotifications(MyEventPriority p) { priorityNotifications[p]--; }
    };

}


#endif
