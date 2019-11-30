package org.rxjava.service.order.person;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rxjava.common.core.annotation.Login;
import org.rxjava.service.order.form.AlipayNotifyForm;
import org.rxjava.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-04-15 23:13
 */
@RestController
public class OrderController {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private OrderService orderService;

    /**
     * 发起支付宝支付
     */
    @PostMapping("aliPay/{orderId}")
    public Mono<String> startAliPay(
            @PathVariable String orderId
    ) {
        return orderService.startAliPay(orderId);
    }

    /**
     * 支付宝回调
     */
    @PostMapping(value = "alipayNotify")
    @Login(false)
    public Mono<Void> alipayNotify(
            ServerWebExchange exchange,
            AlipayNotifyForm form
    ) {
        log.info("收到通知obj:{}", form);
        //fixme:待写收到通知后的处理
        return Mono.just(form)
                .then(exchange.getResponse().writeAndFlushWith(r -> Mono.just("success")));
    }

    /**
     * 微信支付回调
     */
    @PostMapping(value = "weixinNotify")
    @Login(false)
    public Mono<Void> weixinNotify(
            ServerWebExchange exchange, @RequestBody String body
    ) {
        log.info("收到通知:{}", body);
        //fixme:待写收到通知后的处理
        return Mono
                .just(body)
                .then(exchange.getResponse().writeAndFlushWith(r -> Mono.just("<xml>\n" +
                        "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                        "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                        "</xml>\n"))
                );
    }
}
