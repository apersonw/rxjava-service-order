package org.rxjava.service.order.entity;

import lombok.Getter;
import lombok.Setter;
import org.rxjava.common.core.entity.BaseEntity;
import org.rxjava.common.core.entity.Image;
import org.rxjava.service.order.status.OrderStatus;

import java.util.List;

/**
 * @author happy 2019-03-29 13:45
 */
@Getter
@Setter
public class Order extends BaseEntity {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 下单服务：如rxjava-service-card
     */
    private String service;
    /**
     * 下单服务订单Id
     */
    private String serviceOrderId;
    /**
     * 购物中心名称：如充值中心，由下单服务传过来
     */
    private String mallName;
    /**
     * 购物中心LOGO：
     */
    private Image mallLogo;
    /**
     * 订单状态
     */
    private OrderStatus status = OrderStatus.INIT;
    /**
     * 订单商品列表
     */
    private List<OrderGoods> orderGoodsList;
    /**
     * 删除状态
     */
    private boolean delete;
}
