package org.rxjava.service.order.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rxjava.common.core.utils.UUIDUtils;
import org.rxjava.service.order.config.OrderProperties;
import org.rxjava.service.order.model.AliPayTradePayResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author happy 2019/9/22 00:39
 */
@Service
public class AliPayService {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private OrderProperties orderProperties;
    private static final String ALIPAY_GATEWAY_URL = "https://openapi.alipay.com/gateway.do";

    /**
     * 付款
     */
    public Mono<String> pay() {
        return Mono.empty();
    }

    private AliPayTradePayResponseModel appPay(LocalDateTime expireTime, String orderId, long amount, String body, String subject, String returnUrl) {
        OrderProperties.AliPay aliPayProperties = orderProperties.getAliPay();

        AlipayTradeAppPayRequest form = new AlipayTradeAppPayRequest();

        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        Date endTime = Date.from(expireTime.atZone(ZoneOffset.systemDefault()).toInstant());
        model.setBody(body);
        //订单没有标题，此处设置的订单号
        model.setSubject(subject);
        // 外部订单号：设置的订单ID
        model.setOutTradeNo(UUIDUtils.randomUUIDToBase64() + "_" + orderId);
        //订单付款超时时间30分钟
        model.setTimeExpire(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endTime));

        //查询订单应付款并设置总价(单位：元)
        model.setTotalAmount(String.valueOf(amount * 1.0 / 100));//
        model.setProductCode("QUICK_MSECURITY_PAY");

        form.setBizModel(model);
        // 设置异步通知消息地址
        form.setNotifyUrl(aliPayProperties.getNotifyUri());
        //回跳地址
        form.setReturnUrl(returnUrl);

        AlipayClient alipayClient = new DefaultAlipayClient(
                ALIPAY_GATEWAY_URL,
                aliPayProperties.getAppId(),
                aliPayProperties.getAppKey(),
                "json",
                "UTF-8",
                aliPayProperties.getAppSecret(),
                "RSA2");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(form);
            AliPayTradePayResponseModel tradePayResponseModel = new AliPayTradePayResponseModel();

            tradePayResponseModel.setOutTradeNo(model.getOutTradeNo());
            tradePayResponseModel.setTotalAmount(model.getTotalAmount());
            tradePayResponseModel.setBody(response.getBody());

            tradePayResponseModel.setAlipayRequest(form);
            log.info("tradePayResponseModel:{}", tradePayResponseModel);
            return tradePayResponseModel;
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * H5支付
     *
     * @param expireTime 过期时间
     * @param orderId    订单Id
     * @param amount     支付金额
     * @param body       对交易或商品的描述
     * @param subject    订单标题
     * @return AliPayTradePayResponseModel 支付宝返回的交易响应信息
     */
    private AliPayTradePayResponseModel h5Pay(LocalDateTime expireTime, String orderId, long amount, String body, String subject, String returnUrl) {
        OrderProperties.AliPay aliPayProperties = orderProperties.getAliPay();

        AlipayTradeWapPayRequest form = new AlipayTradeWapPayRequest();

        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        Date endTime = Date.from(expireTime.atZone(ZoneOffset.systemDefault()).toInstant());
        model.setBody(body);
        //订单没有标题，此处设置的订单号
        model.setSubject(subject);
        // 外部订单号：设置的订单ID
        model.setOutTradeNo(UUIDUtils.randomUUIDToBase64() + "_" + orderId);
        //订单付款超时时间30分钟
        model.setTimeExpire(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endTime));
        //查询订单应付款并设置总价(单位：元)
        model.setTotalAmount(String.valueOf(amount * 1.0 / 100));
        model.setProductCode("QUICK_MSECURITY_PAY");
        // 设置异步通知消息地址
        form.setNotifyUrl(aliPayProperties.getNotifyUri());
        //回跳地址
        form.setReturnUrl(returnUrl);

        form.setBizModel(model);
        AlipayClient alipayClient = new DefaultAlipayClient(
                ALIPAY_GATEWAY_URL,
                aliPayProperties.getAppId(),
                aliPayProperties.getAppKey(),
                "json",
                "UTF-8",
                aliPayProperties.getAppSecret(),
                "RSA2");

        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(form);
            AliPayTradePayResponseModel tradePayResponseModel = new AliPayTradePayResponseModel();

            tradePayResponseModel.setOutTradeNo(model.getOutTradeNo());
            tradePayResponseModel.setTotalAmount(model.getTotalAmount());
            tradePayResponseModel.setBody(response.getBody());

            tradePayResponseModel.setAlipayRequest(form);
            log.info("tradePayResponseModel:{}", tradePayResponseModel);
            return tradePayResponseModel;
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
