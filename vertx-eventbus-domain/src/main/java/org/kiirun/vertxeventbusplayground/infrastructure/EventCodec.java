package org.kiirun.vertxeventbusplayground.infrastructure;

import org.kiirun.vertxeventbusplayground.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.util.CharsetUtil;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

@Component
public class EventCodec extends CustomCodec<Event> {
   private static final Logger LOGGER = LoggerFactory.getLogger( EventCodec.class );

   @Override
   public void encodeToWire( final Buffer buffer, final Event event ) {
      LOGGER.debug("Encoding event {}", event.getEventId().getId());
      String strJson = JsonObject.mapFrom(event).encode();
      byte[] encoded = strJson.getBytes( CharsetUtil.UTF_8);
      buffer.appendInt(encoded.length);
      Buffer buff = Buffer.buffer(encoded);
      buffer.appendBuffer(buff);
   }

   @Override
   public Event decodeFromWire( final int pos, final Buffer buffer ) {
      int readPos = pos;
      int length = buffer.getInt(readPos);
      readPos += 4;
      byte[] encoded = buffer.getBytes(readPos, readPos + length);
      String str = new String(encoded, CharsetUtil.UTF_8);
      final Event decodedEvent = new JsonObject( str ).mapTo( Event.class );
      LOGGER.debug("Decoded event {}", decodedEvent.getEventId().getId());
      return decodedEvent;
   }

   @Override
   public Event transform( final Event event ) {
      return event;
   }

   @Override
   public String name() {
      return Event.class.getName();
   }

   @Override
   public byte systemCodecID() {
      return -1;
   }
}
