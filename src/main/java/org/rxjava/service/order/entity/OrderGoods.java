package org.rxjava.service.order.entity;

import lombok.Getter;
import lombok.Setter;
import org.rxjava.common.core.entity.Image;

/**
 * @author happy 2019-04-22 20:22
 * 订单商品
 */
@Getter
@Setter
public class OrderGoods {
    /**
     * 商品图标
     */
    private Image icon;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 选择的Sku名称
     */
    private String skuName;
    /**
     * 购买数量
     */
    private int buyNum = 1;
    /**
     * 实付款价
     */
    private int payPrice;
}
