package com.dp.vstore_orderservice.services;

import com.dp.vstore_orderservice.dtos.OrderItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartClient {

    private final RestTemplate restTemplate;

    public CartClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrderItemDto getCart(Long userId) throws  Exception {
        String url = "http://localhost:9092/cart/" + userId;

        return restTemplate.getForObject(url, OrderItemDto.class);
    }
}
