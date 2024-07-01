package com.e_commerce.shop_service.proxy;

import com.e_commerce.shop_service.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "wallet-service", url = "localhost:8081",configuration = FeignClientConfig.class)
public interface WalletProxy {
    @GetMapping("/api/wallet/withdraw/{amount}")
    public ResponseEntity<String> withdraw(@PathVariable long amount);
}
