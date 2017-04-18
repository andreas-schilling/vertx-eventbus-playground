package org.kiirun.vertxeventbusplayground.infrastructure;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import org.kiirun.vertxeventbusplayground.domain.Sendable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Futures;

import io.vertx.core.Future;
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
         Throwables.propagate( e );
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
