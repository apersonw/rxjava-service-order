package org.rxjava.service.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author happy 2019/9/22 00:53
 */
@ConfigurationProperties(prefix = "alipay")
@Data
public class OrderProperties {
    private AliPay aliPay;
    @Data
    public static class AliPay {
        private String appId;
        private String appKey;
        private String appSecret;
        private String notifyUri;
    }
}
