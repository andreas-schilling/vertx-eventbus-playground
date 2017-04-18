package org.kiirun.vertxeventbusreceiver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import org.kiirun.vertxeventbusplayground.domain.Event;
import org.kiirun.vertxeventbusplayground.infrastructure.EventCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver extends AbstractVerticle {
   private static final Logger LOGGER = LoggerFactory.getLogger( Receiver.class );

   public static void main(String[] args) {
      Vertx.clusteredVertx( new VertxOptions( ), result -> {
         result.result().deployVerticle( new Receiver() );
      } );
   }

   @Override
   public void start() throws Exception {
      EventBus eventBus = getVertx().eventBus();
      LOGGER.info("Receiver started...");

      eventBus.registerDefaultCodec(Event.class, new EventCodec());

      eventBus.consumer("playground/event", message -> {
         Event event = (Event) message.body();
         LOGGER.info("Event received: " + event);
      });
   }
}