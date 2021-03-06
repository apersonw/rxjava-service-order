package org.rxjava.service.order.service;

import org.rxjava.service.order.entity.Order;
import org.rxjava.service.order.form.CreateOrderForm;
import org.rxjava.service.order.form.OrderPageForm;
import org.rxjava.service.order.model.OrderModel;
import org.rxjava.service.order.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author happy 2019-04-29 23:45
 * 订单服务
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AliPayService aliPayService;

    public Mono<Page<Order>> findPage(OrderPageForm form, Pageable pageable) {
        return orderRepository
                .findPage(form, pageable);
    }

    public Mono<OrderModel> createOrder(String userId, CreateOrderForm form) {
        Order order = new Order();
        BeanUtils.copyProperties(form, order);
        order.setUserId(userId);
        return orderRepository
                .save(order)
                .map(this::transformToModel);
    }

    private OrderModel transformToModel(Order source) {
        OrderModel target = new OrderModel();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public Mono<String> startAliPay(String orderId) {
        return aliPayService.pay();
    }
}
