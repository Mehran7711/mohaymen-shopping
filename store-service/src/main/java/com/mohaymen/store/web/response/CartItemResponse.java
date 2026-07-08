package com.mohaymen.store.web.response;

import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public record CartItemResponse(
        Long productId, String productName, Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice) {}
