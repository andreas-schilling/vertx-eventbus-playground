package org.kiirun.vertxeventbusplayground.infrastructure;

import org.kiirun.vertxeventbusplayground.domain.Sendable;

import com.google.common.reflect.TypeToken;

import io.vertx.core.eventbus.MessageCodec;

public abstract class CustomCodec<T extends Sendable> implements MessageCodec<T, T> {
   private final TypeToken<T> type = new TypeToken<T>(getClass()) {};

   public Class<T> getDomainClass() {
      return (Class<T>) type.getRawType();
   }
}
