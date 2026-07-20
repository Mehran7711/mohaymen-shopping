package com.mohaymen.store.web.controller;

import com.mohaymen.store.model.UserInfo;
import com.mohaymen.store.service.IOrderService;
import com.mohaymen.store.service.ISecurityService;
import com.mohaymen.store.web.mapper.OrderMapper;
import com.mohaymen.store.web.response.OrderResponse;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "security_auth")
public class OrderController {
    private final IOrderService orderService;
    private final OrderMapper orderMapper;
    private final ISecurityService securityService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@RequestParam String scenario) {
        return ResponseEntity.ok(orderService.checkout(scenario));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrder() {
        String userId = securityService.getLoginUserId();
        UserInfo userInfo = securityService.getCurrentUser(userId);
        log.info("userInfo: {}", userInfo);
        return ResponseEntity.ok(orderMapper.toDto(orderService.getOrder()));
    }
}
