package com.mohaymen.store.web.response;

import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/7/26
 */

public record OrderItemResponse(Long productId,
                                String productName,
                                Integer quantity,
                                BigDecimal unitPrice,
                                BigDecimal totalPrice) {
}
