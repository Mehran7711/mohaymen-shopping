package com.mohaymen.store.web.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Mehran
 * Created on: 7/7/26
 */
@Getter
@Setter
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
}
