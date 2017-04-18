package org.kiirun.vertxeventbusplayground.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Event implements Sendable {
   private EventId eventId;

   private String name;

   private Integer size;

   @JsonCreator
   public Event( @JsonProperty( "eventId" ) final EventId eventId,
         @JsonProperty( "name" ) final String name,
         @JsonProperty( "size" ) final Integer size ) {
      this.eventId = eventId;
      this.name = name;
      this.size = size;
   }

   public EventId getEventId() {
      return eventId;
   }

   public String getName() {
      return name;
   }

   public Integer getSize() {
      return size;
   }

   @Override
   public String toString() {
      return "Event{" +
            "eventId=" + eventId +
            ", name='" + name + '\'' +
            ", size=" + size +
            '}';
   }
}
