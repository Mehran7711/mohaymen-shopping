package com.mohaymen.payment.web.response;

import java.math.BigDecimal;

/**
 * Author: Mehran
 * Created on: 7/8/26
 */
public record RefundResult(boolean success, String transactionId, BigDecimal amount) {}
