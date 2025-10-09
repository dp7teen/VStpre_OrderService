package com.dp.vstore_orderservice.services;

import com.dp.vstore_orderservice.dtos.OrderItemDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartClient {

    private final RestTemplate restTemplate;

    public CartClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        headers.set("Authorization", token);
        return headers;
    }

    public OrderItemDto getCart(Long userId) throws  Exception {
        String url = "http://cart-service:9092/api/cart?userid=" + userId;
        HttpEntity<?> entity = new HttpEntity<>(getHeaders());
        return restTemplate.exchange(url, HttpMethod.GET, entity, OrderItemDto.class).getBody();
    }
}
