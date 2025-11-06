package com.example.pgtest.payment.dto;

public record PaymentConfirmRequestDto(
        String orderId,
        String paymentKey,
        int amount
) {
}
