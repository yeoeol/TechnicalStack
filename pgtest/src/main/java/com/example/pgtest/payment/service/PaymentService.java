package com.example.pgtest.payment.service;

import com.example.pgtest.payment.client.TossPaymentsClient;
import com.example.pgtest.payment.domain.Payment;
import com.example.pgtest.payment.dto.PaymentConfirmRequestDto;
import com.example.pgtest.payment.dto.PaymentConfirmResponseDto;
import com.example.pgtest.payment.client.dto.TossResponseDto;
import com.example.pgtest.payment.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TossPaymentsClient tossPaymentsClient;

    public void createPayment(String orderId, Long userId, int amount) {
        Payment payment = Payment.builder()
                .userId(userId)
                .orderId(orderId)
                .totalAmount(amount)
                .build();

        paymentRepository.save(payment);
    }

    @Transactional
    public PaymentConfirmResponseDto confirmPayment(String jsonBody) {
        // 토스 페이먼츠 승인 API 요청
        PaymentConfirmRequestDto requestDto = parseRequestData(jsonBody);

        TossResponseDto tossResponseDto = tossPaymentsClient.confirmPayment(requestDto);

        Payment payment = paymentRepository.findByOrderId(tossResponseDto.orderId())
                .orElseThrow(() -> new RuntimeException("Payment NOT FOUND : By orderId"));
        payment.setting(tossResponseDto);

        return PaymentConfirmResponseDto.from(payment);
    }

    private PaymentConfirmRequestDto parseRequestData(String jsonBody) {
        try {
            return objectMapper.readValue(jsonBody, PaymentConfirmRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("RequestData Object parsing Error", e);
        }
    }
}
