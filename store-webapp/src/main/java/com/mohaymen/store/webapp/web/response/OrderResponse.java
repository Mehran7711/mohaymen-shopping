package com.mohaymen.store.webapp.web.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public record OrderResponse(
        Long id,
        String orderNumber,
        String status,
        BigDecimal totalAmount,
        UserInfo customer,
        List<OrderItemResponse> items) {
}
