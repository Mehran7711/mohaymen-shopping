package com.mohaymen.store.webapp.web.response;

import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/28/26
 */

public record ProductDto(Long id, String code, String name, String description, String imageUrl, BigDecimal price) {}

