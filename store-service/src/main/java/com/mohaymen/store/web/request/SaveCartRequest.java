package com.mohaymen.store.web.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Author: Mehran
 * Created on: 7/7/26
 */

@Getter
@Setter
public class SaveCartRequest {

    private Long customerId;
    private List<CartItemRequest> items;
}