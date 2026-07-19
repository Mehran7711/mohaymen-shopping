package com.mohaymen.store.web.controller;

import com.mohaymen.store.service.ICartService;
import com.mohaymen.store.web.request.CartItemRequest;
import com.mohaymen.store.web.response.CartResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "security_auth")
public class CartController {
    private final ICartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addProduct(@RequestBody CartItemRequest request) {

        return ResponseEntity.ok(cartService.addProduct(request));
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartResponse> updateQuantity(
            @PathVariable Long productId, @RequestBody CartItemRequest request) {

        return ResponseEntity.ok(cartService.updateQuantity(productId, request));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long productId, @RequestParam Long customerId) {

        cartService.removeProduct(customerId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestParam Long customerId) {

        return ResponseEntity.ok(cartService.getCart(customerId));
    }
}
