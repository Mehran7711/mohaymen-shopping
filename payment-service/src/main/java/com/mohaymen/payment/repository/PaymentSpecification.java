package com.mohaymen.payment.repository;

import com.mohaymen.payment.model.Payment;
import com.mohaymen.payment.web.filter.PaymentFilter;
import org.springframework.data.jpa.domain.Specification;

/**
 * Author: Mehran
 * Created on: 7/14/26
 */
public final class PaymentSpecification {

    public static Specification<Payment> hasOrderId(Long orderId) {
        return (root, query, cb) -> orderId == null ? null : cb.equal(root.get("orderId"), orderId);
    }

    private static Specification<Payment> hasStatus(String status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Payment> build(PaymentFilter filter) {
        return Specification.where(hasOrderId(filter.orderId())).and(hasStatus(filter.status()));
    }
}
