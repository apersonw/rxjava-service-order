package org.rxjava.service.order.inner;

import org.rxjava.common.core.entity.LoginInfo;
import org.rxjava.service.order.form.CreateOrderForm;
import org.rxjava.service.order.model.OrderModel;
import org.rxjava.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author happy 2019/9/20 16:30
 */
@RestController
@RequestMapping("inner")
public class InnerPayController {
    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("order")
    public Mono<OrderModel> createOrder(
            @Valid CreateOrderForm form,
            LoginInfo loginInfo
    ) {
        return orderService.createOrder(loginInfo.getUserId(), form);
    }
}
