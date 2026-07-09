package com.mohaymen.store.web.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

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
