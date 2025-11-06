package com.example.pgtest.payment.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "payment")
public class PaymentProperties {

    private String clientKey;
    private String secretKey;
    private String baseUrl;
    private String url;
    private String confirmEndpoint;

    public String getUrl() {
        return baseUrl+confirmEndpoint;
    }
}
