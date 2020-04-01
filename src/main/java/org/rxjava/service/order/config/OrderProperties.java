package org.rxjava.service.order.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author happy 2019/9/22 00:53
 */
@Data
@ConfigurationProperties(prefix = "order")
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
