package com.mohaymen.store.web.controller;

import com.mohaymen.store.service.IOrderService;
import com.mohaymen.store.web.mapper.OrderMapper;
import com.mohaymen.store.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@RequestParam Long customerId) {
        return ResponseEntity.ok(orderService.checkout(customerId));
    }

    @GetMapping
    public ResponseEntity<OrderResponse> getOrder(@RequestParam Long customerId) {
        return ResponseEntity.ok(orderMapper.toDto(orderService.getOrder(customerId)));
    }
}
