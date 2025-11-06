package com.example.pgtest.payment.domain;

import com.example.pgtest.payment.client.dto.TossResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "payments")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    private Long userId;
    private Long reservationId;

    private String paymentKey;
    private String orderId;
    private String orderName;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
    private int totalAmount;

    public void setting(TossResponseDto tossResponseDto) {
        this.paymentKey = tossResponseDto.paymentKey();
        this.orderName = tossResponseDto.orderName();
        this.status = tossResponseDto.status();
        this.requestedAt = tossResponseDto.requestedAt().toLocalDateTime();
        this.approvedAt = tossResponseDto.approvedAt().toLocalDateTime();
        this.totalAmount = tossResponseDto.totalAmount();
    }
}
