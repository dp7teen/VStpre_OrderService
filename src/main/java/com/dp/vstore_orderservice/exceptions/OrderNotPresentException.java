package com.dp.vstore_orderservice.exceptions;

public class OrderNotPresentException extends Exception {
    public OrderNotPresentException(String message) {
        super(message);
    }
}
