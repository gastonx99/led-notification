#include <string>

#ifndef __EVENT_H_INCLUDED__
#define __EVENT_H_INCLUDED__

using namespace std;

namespace lednotification {

    enum EventType { UNKNOWN, NEW_ALERT, CANCEL_ALERT, NEW_NOTIFICATION, CANCEL_NOTIFICATION, PING, PONG };
    enum EventPriority { CRITICAL, HIGH, MEDIUM, LOW };

    class Event {
        EventType type;
        string source;
        EventPriority priority;

      public:
        Event(string s, EventType t) { source = s; type = t; }
        Event(string s, EventType t, EventPriority p) { source = s; type = t; priority = p; }
        Event(string s, string t);
        Event(string s, string t, string p);
        Event();

        string getSource() { return source; }
        EventType getType() { return type; }
        EventPriority getPriority() { return priority; }
        void setSource(string s) { source = s; }
        void setType(EventType t) { type = t; }
        void setPriority(EventPriority p) { priority = p; }
    };

}

#endif
