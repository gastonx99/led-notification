#include "spark_wiring_usbserial.h"

#include "EventService.h"
#include "MyEvent.h"

namespace lednotification {

    void EventService::initialize(int port) {
        udp.begin(port);
        Serial.begin(9600);
        Serial.println(WiFi.localIP());
        IPAddress multicastAddress(239,255,0,0);
        udp.joinMulticast(multicastAddress);
    }

    MyEvent* EventService::parseEvent(char* message) {
      char* sourcePart = std::strtok(message, ";");
      char* typePart = std::strtok(NULL, ";");
      char* priorityPart = std::strtok(NULL, ";");
      char* source = std::strtok(NULL, std::strtok(sourcePart, "="));
      char* type = std::strtok(NULL, std::strtok(typePart, "="));
      char* priority = priorityPart == NULL ? NULL : std::strtok(NULL, std::strtok(priorityPart, "="));
      if(priority == NULL) {
        return new MyEvent(source, type);
      } else {
        return new MyEvent(source, type, priority);
      }
    }

    MyEvent* EventService::readEvent() {
      MyEvent* event = NULL;
      int available = udp.parsePacket();
      if (available > 0) {
        udp.read(buffer, available);
        buffer[available] = 0;
        Serial.println("Received event");
        Serial.println(buffer);
        event = parseEvent(buffer);
        Serial.printlnf("Parsed event %s", event->getType());
      }
      return event;
    }

    void EventService::sendPong() {
      udp.write("source=BUTTON TEST CLIENT;type=PONG");
    }

}
