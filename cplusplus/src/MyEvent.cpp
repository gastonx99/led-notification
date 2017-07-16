#include <map>
#include "spark_wiring_usbserial.h"
#include "MyEvent.h"

namespace lednotification {

    map< std::string, MyEventType> eventTypeDictionary = {
        {"NEW_ALERT", MyEventType::NEW_ALERT},
        {"CANCEL_ALERT", MyEventType::CANCEL_ALERT},
        {"NEW_NOTIFICATION", MyEventType::NEW_NOTIFICATION},
        {"CANCEL_NOTIFICATION", MyEventType::CANCEL_NOTIFICATION},
        {"PING", MyEventType::PING},
        {"PONG", MyEventType::PONG}
    };

    map< std::string, MyEventPriority> eventPriorityDictionary = {
        {"CRITICAL", MyEventPriority::CRITICAL},
        {"HIGH", MyEventPriority::HIGH},
        {"MEDIUM", MyEventPriority::MEDIUM},
        {"LOW", MyEventPriority::LOW}
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
}

