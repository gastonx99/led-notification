#ifndef __EVENTSERVICE_H_INCLUDED__
#define __EVENTSERVICE_H_INCLUDED__

#include <InternetButton.h>
#include "MyEvent.h"

#define EVENT_BUFFER_SIZE 512

namespace lednotification {
    class EventService {
        private:
            UDP udp;
            char buffer [EVENT_BUFFER_SIZE] = {};

            MyEvent* parseEvent(char* message);
        public:
            void initialize(int port);
            MyEvent* readEvent();
            void sendPong();
    };
}

#endif
