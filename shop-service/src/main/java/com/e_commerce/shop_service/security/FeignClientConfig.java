package com.e_commerce.shop_service.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String authHeader = CredentialsCaptureFilter.authHeader.get();
                if (authHeader != null) {
                    template.header("Authorization", authHeader);
                }
            }
        };
    }
}
