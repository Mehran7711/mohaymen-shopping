package com.mohaymen.store.webapp.web.controller;

import com.mohaymen.store.webapp.service.IOrderService;
import com.mohaymen.store.webapp.web.response.OrderErrorResponse;
import com.mohaymen.store.webapp.web.response.OrderResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

@Controller
@RequestMapping("/order")
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final IOrderService orderService;

    OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }
    //    private final SecurityHelper securityHelper;
    //
    //    OrderController(OrderServiceClient orderServiceClient, SecurityHelper securityHelper) {
    //        this.orderServiceClient = orderServiceClient;
    //        this.securityHelper = securityHelper;
    //    }

    //    @GetMapping("/cart")
    //    String cart() {
    //        return "cart";
    //    }

    //    @PostMapping("/api/orders")
    //    @ResponseBody
    //    OrderConfirmationDTO createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
    //        log.info("Creating order: {}", orderRequest);
    //        return orderServiceClient.createOrder(getHeaders(), orderRequest);
    //    }
    //
    @GetMapping("/load/{orderNumber}")
    String showOrderDetails(@PathVariable String orderNumber, Model model) {
        model.addAttribute("orderNumber", orderNumber);
        return "order_details";
    }

    //
    @GetMapping("/get/{orderNumber}")
    @ResponseBody
    OrderResponse getOrder(Authentication authentication, @PathVariable String orderNumber) {
        log.info("Fetching order details for orderNumber: {}", orderNumber);
        return orderService.getOrder(authentication, orderNumber);
    }

    //
    @GetMapping("/orders")
    String showOrders() {
        return "orders";
    }

    //
    @GetMapping("/all")
    @ResponseBody
    List<OrderResponse> getOrders(Authentication authentication) {
        log.info("Fetching orders");
        return orderService.getAllOrders(authentication);
    }
    //
    //    private Map<String, ?> getHeaders() {
    //        String accessToken = securityHelper.getAccessToken();
    //        return Map.of("Authorization", "Bearer " + accessToken);
    //    }

    @PostMapping("/checkout")
    @ResponseBody
    ResponseEntity<?> checkout(Authentication authentication, @Valid @RequestParam String scenario) {
        log.info("Creating order: {}", scenario);
        try {
            return ResponseEntity.ok(orderService.checkout(authentication, scenario));
        } catch (RestClientResponseException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(new OrderErrorResponse(readMessage(ex)));
        }
    }

    private static String readMessage(RestClientResponseException ex) {
        String body = ex.getResponseBodyAsString();
        if (body == null || body.isBlank()) {
            return "Order creation failed";
        }
        try {
            Object message = new JacksonJsonParser().parseMap(body).get("message");
            if (message instanceof String text && !text.isBlank()) {
                return text;
            }
        } catch (RuntimeException ignored) {
            log.warn("Could not read the order error response");
        }
        return "Order creation failed";
    }
}
