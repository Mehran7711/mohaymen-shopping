package com.mohaymen.store.webapp.web.controller;

import com.mohaymen.store.webapp.service.ICartService;
import com.mohaymen.store.webapp.service.IProductService;
import com.mohaymen.store.webapp.web.request.AddCartItemRequest;
import com.mohaymen.store.webapp.web.response.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Mehran
 * Created on: 7/28/26
 */

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;
    private final IProductService productService;

    @GetMapping("/cart")
    String cart() {
        return "cart";
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication));
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addItem(Authentication authentication, @RequestBody AddCartItemRequest request) {
        productService.addProduct(authentication, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/items/{productId}")
    @ResponseBody
    public CartResponse updateQuantity(Authentication authentication,
                                               @PathVariable Long productId,
                                               @RequestBody AddCartItemRequest request) {
        return cartService.updateQuantity(authentication, productId, request);

    }
}
