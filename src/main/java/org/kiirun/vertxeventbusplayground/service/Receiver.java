package org.kiirun.vertxeventbusplayground.service;

import org.kiirun.vertxeventbusplayground.domain.Event;
import org.kiirun.vertxeventbusplayground.infrastructure.Addresses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

@Component
public class Receiver extends AbstractVerticle{
   private static final Logger LOGGER = LoggerFactory.getLogger( Receiver.class );

   private final EventBus eventBus;

   public Receiver( final EventBus eventBus ) {
      super();
      this.eventBus = eventBus;
   }

   @Override
   public void start( final Future<Void> startFuture ) throws Exception {
      eventBus.consumer( Addresses.EVENTS, message -> {
         Event event = (Event) message.body();
         LOGGER.info("Receiving event: " + event);
      });
      startFuture.complete();
   }
}
