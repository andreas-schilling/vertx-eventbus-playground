package org.kiirun.vertxeventbusplayground.service;

import java.util.Random;
import java.util.UUID;

import org.kiirun.vertxeventbusplayground.domain.Event;
import org.kiirun.vertxeventbusplayground.domain.EventId;
import org.kiirun.vertxeventbusplayground.transport.Addresses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

@Component
public class Sender extends AbstractVerticle {
   private static final Logger LOGGER = LoggerFactory.getLogger( Sender.class );

   private final EventBus eventBus;

   public Sender( final EventBus eventBus ) {
      super();
      this.eventBus = eventBus;
   }

   @Override
   public void start( final Future<Void> startFuture ) throws Exception {
      vertx.setPeriodic( 1000, timerId -> {
         final int size = new Random().nextInt();
         final Event event = new Event( EventId.create( UUID.randomUUID().toString() ), "SomeEvent[" + size + "]", size,
               ImmutableMap.of( "firstProperty", "isAString", "secondProperty", 100 ) );
         LOGGER.info( "Sending event message: {}", event );
         eventBus.publish( Addresses.EVENTS.address(), event );
         Addresses.EVENTS_PLAIN.publish( eventBus, event );
      } );
      startFuture.complete();
   }
}
