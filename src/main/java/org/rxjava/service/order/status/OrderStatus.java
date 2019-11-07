package org.rxjava.service.order.status;

/**
 * @author happy 2019/9/20 14:05
 */
public enum OrderStatus {
    /**
     * 初始化
     */
    INIT,
    /**
     * 已付款
     */
    PAY,
    /**
     * 已发货
     */
    DELIVER,
    /**
     * 已签收
     */
    SIGN,
    /**
     * 已取消
     */
    CANCEL
}
