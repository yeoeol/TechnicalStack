package com.example.pgtest.payment.client;

import com.example.pgtest.payment.config.TossFeignConfig;
import com.example.pgtest.payment.dto.PaymentConfirmRequestDto;
import com.example.pgtest.payment.client.dto.TossResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "toss-payments-client", url = "${payment.url}", configuration = TossFeignConfig.class)
public interface TossPaymentsClient {
    /**
     * 1. @PostMapping: POST /v1/payments/confirm 경로로 요청
     * 2. @RequestBody: request 객체를 JSON으로 직렬화 (objectMapper.writeValue(os, request) 대체)
     * 3. TossResponseDto: 응답 JSON을 DTO로 역직렬화 (objectMapper.readValue(reader, ...) 대체)
     */
    @PostMapping("/v1/payments/confirm") // 베이스 URL 뒤에 붙는 상세 경로
    TossResponseDto confirmPayment(@RequestBody PaymentConfirmRequestDto request);
}
