package org.kiirun.vertxeventbusplayground.infrastructure;

import org.kiirun.vertxeventbusplayground.domain.Event;
import org.springframework.stereotype.Component;

@Component
public class EventCodec extends CustomCodec<Event> {
}
