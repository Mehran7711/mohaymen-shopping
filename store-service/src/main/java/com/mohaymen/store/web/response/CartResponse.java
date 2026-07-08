package com.mohaymen.store.web.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public record CartResponse(Long id, BigDecimal totalAmount, List<CartItemResponse> items) {}
