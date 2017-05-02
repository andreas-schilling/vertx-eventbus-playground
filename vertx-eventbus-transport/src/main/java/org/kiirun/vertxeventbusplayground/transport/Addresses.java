package org.kiirun.vertxeventbusplayground.transport;

import org.kiirun.vertxeventbusplayground.domain.Event;
import org.kiirun.vertxeventbusplayground.domain.Sendable;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

public enum Addresses {
   EVENTS( "playground/event", Event.class ),

   EVENTS_PLAIN( "playground/plain/event", Event.class ),;

   private final String address;

   private final Class<?> clazz;

   private Addresses( final String address, final Class<?> clazz ) {
      this.address = address;
      this.clazz = clazz;
   }

   public String address() {
      return address;
   }

   public <T extends Sendable> MessageConsumer<T> consume( final EventBus eventBus, final Handler<T> handler ) {
      return eventBus.consumer( address, message -> {
         handler.handle( ((JsonObject) message.body()).mapTo( (Class<T>) clazz ) );
      } );
   }

   public <T extends Sendable> EventBus publish( final EventBus eventBus, final T objectToSend ) {
      return eventBus.publish( address, JsonObject.mapFrom( objectToSend ) );
   }
}
