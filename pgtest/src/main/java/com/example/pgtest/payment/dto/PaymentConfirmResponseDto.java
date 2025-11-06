package com.example.pgtest.payment.dto;

import com.example.pgtest.payment.domain.Payment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaymentConfirmResponseDto(
        Long userId,
        Long reservationId,
        String paymentKey,
        String orderId,
        String orderName,
        String status,
        LocalDateTime requestedAt,
        LocalDateTime approvedAt,
        int totalAmount
) {
    public static PaymentConfirmResponseDto from(Payment payment) {
        return PaymentConfirmResponseDto.builder()
                .userId(payment.getUserId())
                .reservationId(payment.getReservationId())
                .paymentKey(payment.getPaymentKey())
                .orderId(payment.getOrderId())
                .orderName(payment.getOrderName())
                .status(payment.getStatus())
                .requestedAt(payment.getRequestedAt())
                .approvedAt(payment.getApprovedAt())
                .totalAmount(payment.getTotalAmount())
                .build();
    }
}
