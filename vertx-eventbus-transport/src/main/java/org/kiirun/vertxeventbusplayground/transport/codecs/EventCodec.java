package org.kiirun.vertxeventbusplayground.transport.codecs;

import org.kiirun.vertxeventbusplayground.domain.Event;
import org.springframework.stereotype.Component;

@Component
public class EventCodec extends CustomCodec<Event> {
}
