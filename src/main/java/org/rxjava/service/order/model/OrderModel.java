package org.rxjava.service.order.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author happy 2019-04-15 23:14
 */
@Data
public class OrderModel {
    private String id;
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 创建日期
     */
    private LocalDateTime createDate;
}
