package com.dp.vstore_orderservice.controllerAdvices;

import com.dp.vstore_orderservice.exceptions.IncorrectAmountException;
import com.dp.vstore_orderservice.exceptions.OrderCannotBePlacedException;
import com.dp.vstore_orderservice.exceptions.OrderNotPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<Map<String, String>> handle(String message, HttpStatus status) {
        Map<String, String> map = new HashMap<>();
        map.put("error", message);
        return new ResponseEntity<>(map, status);
    }

    @ExceptionHandler(IncorrectAmountException.class)
    public ResponseEntity<Map<String, String>> handleIncorrectAmountException(IncorrectAmountException ex) {
        return handle(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCannotBePlacedException.class)
    public ResponseEntity<Map<String, String>> handleOrderCannotBePlacedException(OrderCannotBePlacedException ex) {
        return handle(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotPresentException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotPresentException(OrderNotPresentException ex) {
        return handle(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        return handle("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
