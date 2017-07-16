#include <map>
#include "spark_wiring_usbserial.h"
#include "Event.h"

namespace lednotification {

    map< std::string, EventType> eventTypeDictionary = {
        {"NEW_ALERT", EventType::NEW_ALERT},
        {"CANCEL_ALERT", EventType::CANCEL_ALERT},
        {"NEW_NOTIFICATION", EventType::NEW_NOTIFICATION},
        {"CANCEL_NOTIFICATION", EventType::CANCEL_NOTIFICATION},
        {"PING", EventType::PING},
        {"PONG", EventType::PONG}
    };

    map< std::string, EventPriority> eventPriorityDictionary = {
        {"CRITICAL", EventPriority::CRITICAL},
        {"HIGH", EventPriority::HIGH},
        {"MEDIUM", EventPriority::MEDIUM},
        {"LOW", EventPriority::LOW}
    };

    EventType lookupEventType(std::string s) {
      Serial.println("Looking up event type");
      Serial.println(s.c_str());
      map< std::string, EventType >::iterator i = eventTypeDictionary.find(s);
      if( i != eventTypeDictionary.end()) {
          return i->second;
      }
    }

    EventPriority lookupEventPriority(std::string s) {
      Serial.println("Looking up event priority");
      Serial.println(s.c_str());
      map< std::string, EventPriority >::iterator i = eventPriorityDictionary.find(s);
      if( i != eventPriorityDictionary.end()) {
          return i->second;
      }
    }

    Event::Event(std::string s, std::string t) {
      Serial.println("Howdy");
      source = s;
      type = lookupEventType(t);
    }

    Event::Event(std::string s, std::string t, std::string p) {
      source = s;
      type = lookupEventType(t);
      priority = lookupEventPriority(p);
    }
}

