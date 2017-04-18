package org.kiirun.vertxeventbusplayground.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventId {
   private String id;

   private EventId( final String id ) {
      this.id = id;
   }

   @JsonCreator
   public static EventId create( @JsonProperty("id") final String id ) {
      return new EventId( id );
   }

   public String getId() {
      return id;
   }

   @Override
   public String toString() {
      return "EventId{" +
            "id='" + id + '\'' +
            '}';
   }
}
