package org.rxjava.service.order.model;

import com.alipay.api.AlipayRequest;
import lombok.Data;

import java.util.Map;

/**
 * @author happy 2019/9/22 01:12
 */
@Data
public class AliPayTradePayResponseModel {
    /**
     * 支付请求参数信息
     */
    private AlipayRequest alipayRequest;
    private String code;
    private String msg;
    private String subCode;
    private String subMsg;
    private String body;
    private Map<String, String> params;
    /**
     * 商户网站唯一订单号
     */
    private String outTradeNo;
    /**
     * 收款支付宝账号对应的支付宝唯一用户号。
     * 以2088开头的纯16位数字
     */
    private String sellerId;
    /**
     * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
     */
    private String totalAmount;
    /**
     * 该交易在支付宝系统中的交易流水号。最长64位。
     */
    private String tradeNo;
}
