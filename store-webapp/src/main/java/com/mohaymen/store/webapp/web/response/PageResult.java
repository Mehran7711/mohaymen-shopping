package com.mohaymen.store.webapp.web.response;

import java.util.List;

/**
 * Author: Mehran
 * Created on: 7/1/26
 */
public record PageResult<T>(
        List<T> data,
        long totalElements,
        int pageNumber,
        int totalPages,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious) {}
