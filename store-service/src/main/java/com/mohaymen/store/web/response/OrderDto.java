package com.mohaymen.store.web.response;


import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public record OrderDto(String orderNumber, Long customerId, String status, BigDecimal totalAmount) {}
