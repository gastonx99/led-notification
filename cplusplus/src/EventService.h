#ifndef __EVENTSERVICE_H_INCLUDED__
#define __EVENTSERVICE_H_INCLUDED__

#include <InternetButton.h>
#include "Event.h"

#define EVENT_BUFFER_SIZE 512

namespace lednotification {
    class EventService {
        private:
            UDP udp;
            char buffer [EVENT_BUFFER_SIZE] = {};

            Event* parseEvent(char* message);
        public:
            void initialize(int port);
            Event* readEvent();
            void sendPong();
    };
}

#endif
