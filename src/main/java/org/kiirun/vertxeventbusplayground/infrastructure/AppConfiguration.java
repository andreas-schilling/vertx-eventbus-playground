package org.kiirun.vertxeventbusplayground.infrastructure;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.kiirun.vertxeventbusplayground.domain.Sendable;
import org.kiirun.vertxeventbusplayground.transport.codecs.CustomCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;

@Configuration
public class AppConfiguration {
   private static final Logger LOGGER = LoggerFactory.getLogger( AppConfiguration.class );

   @Bean
   public Vertx vertx() {
      AtomicReference<Vertx> vertx = new AtomicReference();
      CountDownLatch latch = new CountDownLatch( 1 );
      Vertx.clusteredVertx( new VertxOptions(), result -> {
         vertx.set( result.result() );
         latch.countDown();
      } );
      try {
         latch.await();
      } catch ( InterruptedException e ) {
         throw new RuntimeException( e );
      }
      return vertx.get();
   }

   @Bean
   public EventBus eventBus( List<? extends CustomCodec<? extends Sendable>> allCodecs ) {
      final EventBus eventBus = vertx().eventBus();
      allCodecs.stream().forEach( codec ->
            eventBus.registerDefaultCodec( (Class) codec.getDomainClass(), codec ) );
      return eventBus;
   }
}
