package com.dp.vstore_orderservice.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductClient {
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        headers.set("Authorization", token);
        return headers;
    }

    public void updateStock(Long productId, int quantityToDeduct) throws Exception {
        String url = String.format("http://product-service:9091/api/products/update/%s/%s", productId,
                quantityToDeduct);
        HttpEntity<?> entity = new HttpEntity<>(getHeaders());
        restTemplate.exchange(url, HttpMethod.PUT ,entity, Boolean.class);
    }
}
