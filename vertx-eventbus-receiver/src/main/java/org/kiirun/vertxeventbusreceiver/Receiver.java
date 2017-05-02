package org.kiirun.vertxeventbusreceiver;

import org.kiirun.vertxeventbusplayground.domain.Event;
import org.kiirun.vertxeventbusplayground.transport.Addresses;
import org.kiirun.vertxeventbusplayground.transport.codecs.EventCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;

public class Receiver extends AbstractVerticle {
   private static final Logger LOGGER = LoggerFactory.getLogger( Receiver.class );

   public static void main( String[] args ) {
      Vertx.clusteredVertx( new VertxOptions(), result -> {
         result.result().deployVerticle( new Receiver() );
      } );
   }

   @Override
   public void start() throws Exception {
      EventBus eventBus = getVertx().eventBus();
      LOGGER.info( "Receiver started..." );

      eventBus.registerDefaultCodec( Event.class, new EventCodec() );

      eventBus.consumer( Addresses.EVENTS.address(), message -> {
         Event event = (Event) message.body();
         LOGGER.info( "Remote Event received through custom codec: {}", event );
      } );

      Addresses.EVENTS_PLAIN.consume( eventBus, ( Event event ) -> {
         LOGGER.info( "Remote Event received through adapter without codec: {}", event );
      } );
   }
}
