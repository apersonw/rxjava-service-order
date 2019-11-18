package org.rxjava.service.order.bus;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rxjava.common.bus.BusEvent;
import org.rxjava.common.bus.BusReceiver;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.stereotype.Component;

@Component
public class OrderBusReceiver implements BusReceiver {
    private static final Logger log = LogManager.getLogger();

    @Override
    public void receiveMessage(BusEvent busEvent) {
        System.out.println(busEvent);
    }
}
