#include <map>
#include "spark_wiring_usbserial.h"
#include "MyEvent.h"

map< std::string, MyEventType> eventTypeDictionary = {
{"NEW_ALERT", NEW_ALERT},
{"CANCEL_ALERT", CANCEL_ALERT},
{"NEW_NOTIFICATION", NEW_NOTIFICATION},
{"CANCEL_NOTIFICATION", CANCEL_NOTIFICATION},
{"PING", PING},
{"PONG", PONG}
};

map< std::string, MyEventPriority> eventPriorityDictionary = {
{"XCRITICAL", XCRITICAL},
{"XHIGH", XHIGH},
{"XMEDIUM", XMEDIUM},
{"XLOW", XLOW}
};

MyEventType lookupEventType(std::string s) {
  Serial.println("Looking up event type");
  Serial.println(s.c_str());
  map< std::string, MyEventType >::iterator i = eventTypeDictionary.find(s);
  if( i != eventTypeDictionary.end()) {
      return i->second;
  }
}

MyEventPriority lookupEventPriority(std::string s) {
  Serial.println("Looking up event priority");
  Serial.println(s.c_str());
  map< std::string, MyEventPriority >::iterator i = eventPriorityDictionary.find(s);
  if( i != eventPriorityDictionary.end()) {
      return i->second;
  }
}

MyEvent::MyEvent(std::string s, std::string t) {
  Serial.println("Howdy");
  source = s;
  type = lookupEventType(t);
}

MyEvent::MyEvent(std::string s, std::string t, std::string p) {
  source = s;
  type = lookupEventType(t);
  priority = lookupEventPriority(p);
}
