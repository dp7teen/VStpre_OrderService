package com.dp.vstore_orderservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductClient {
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void updateStock(Long productId, int quantityToDeduct) {
        String url = String.format("http://localhost:9091/products/update/%s/%s", productId, quantityToDeduct);

        restTemplate.patchForObject(url, String.class, null);
    }
}
