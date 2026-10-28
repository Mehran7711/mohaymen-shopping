package com.mohaymen.store.web.response;

import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public record ProductDto(Long id, String code, String name, String description, String imageUrl, BigDecimal price, Long quantity) {}
