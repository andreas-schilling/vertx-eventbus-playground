package org.kiirun.vertxeventbusplayground.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertx.core.json.JsonObject;

public class Event implements Sendable {
   private EventId eventId;

   private String name;

   private Integer size;

   private JsonObject additionalProperties;

   @JsonCreator
   public Event( @JsonProperty( "eventId" ) final EventId eventId,
         @JsonProperty( "name" ) final String name,
         @JsonProperty( "size" ) final Integer size,
         @JsonProperty( "additionalProperties" ) final Map<String, Object> additionalProperties) {
      this.eventId = eventId;
      this.name = name;
      this.size = size;
      this.additionalProperties = new JsonObject( additionalProperties );
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

   public JsonObject getAdditionalProperties() {
      return additionalProperties.copy();
   }

   @Override
   public String toString() {
      return "Event{" +
            "eventId=" + eventId +
            ", name='" + name + '\'' +
            ", size=" + size +
            ", additionalProperties=" + additionalProperties.encodePrettily() +
            '}';
   }
}
