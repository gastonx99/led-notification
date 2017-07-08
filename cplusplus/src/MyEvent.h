#ifndef __EVENT_H_INCLUDED__
#define __EVENT_H_INCLUDED__

#include <string>
using namespace std;

enum MyEventType { UNKNOWN, NEW_ALERT, CANCEL_ALERT, NEW_NOTIFICATION, CANCEL_NOTIFICATION, PING, PONG };
enum MyEventPriority { XUNKNOWN, XCRITICAL, XHIGH, XMEDIUM, XLOW };

class MyEvent {
    MyEventType type;
    string source;
    MyEventPriority priority;

  public:
    MyEvent(string s, MyEventType t) { source = s; type = t; }
    MyEvent(string s, MyEventType t, MyEventPriority p) { source = s; type = t; priority = p; }
    MyEvent(string s, string t);
    MyEvent(string s, string t, string p);
    MyEvent();

    string getSource() { return source; }
    MyEventType getType() { return type; }
    MyEventPriority getPriority() { return priority; }
    void setSource(string s) { source = s; }
    void setType(MyEventType t) { type = t; }
    void setPriority(MyEventPriority p) { priority = p; }
};

#endif
