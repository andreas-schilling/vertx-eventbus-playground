package org.kiirun.vertxeventbusplayground.transport.codecs;

import org.kiirun.vertxeventbusplayground.domain.Sendable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;

import io.netty.util.CharsetUtil;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public abstract class CustomCodec<T extends Sendable> implements MessageCodec<T, T> {
   private static final Logger LOGGER = LoggerFactory.getLogger( CustomCodec.class );

   private final TypeToken<T> type = new TypeToken<T>( getClass() ) {
   };

   public Class<T> getDomainClass() {
      return (Class<T>) type.getRawType();
   }

   @Override
   public void encodeToWire( final Buffer buffer, final T sendable ) {
      LOGGER.debug( "Encoding {}", sendable );
      String strJson = JsonObject.mapFrom( sendable ).encode();
      byte[] encoded = strJson.getBytes( CharsetUtil.UTF_8 );
      buffer.appendInt( encoded.length );
      Buffer buff = Buffer.buffer( encoded );
      buffer.appendBuffer( buff );
   }

   @Override
   public T decodeFromWire( final int pos, final Buffer buffer ) {
      int readPos = pos;
      int length = buffer.getInt( readPos );
      readPos += 4;
      byte[] encoded = buffer.getBytes( readPos, readPos + length );
      String str = new String( encoded, CharsetUtil.UTF_8 );
      final T decodedSendable = new JsonObject( str ).mapTo( getDomainClass() );
      LOGGER.debug( "Decoded {}", decodedSendable );
      return decodedSendable;
   }

   @Override
   public T transform( final T sendable ) {
      return sendable;
   }

   @Override
   public String name() {
      return getDomainClass().getName();
   }

   @Override
   public byte systemCodecID() {
      return -1;
   }
}
