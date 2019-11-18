package org.rxjava.service.order;

import org.rxjava.common.bus.EnableBus;
import org.rxjava.service.order.config.OrderProperties;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-03-17 22:10
 */
@SpringBootApplication
@EnableConfigurationProperties(OrderProperties.class)
@EnableBus
public class RxServiceOrderApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(RxServiceOrderApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }
}
