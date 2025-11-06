package com.example.pgtest.payment.controller;

import com.example.pgtest.payment.dto.PaymentConfirmResponseDto;
import com.example.pgtest.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PaymentApiController {

    private final PaymentService paymentService;

    @GetMapping("/")
    public String test() {
        return "test";
    }

    @PostMapping("/api/payments/confirm")
    public PaymentConfirmResponseDto confirm(@RequestBody String jsonBody) throws IOException {
        return paymentService.confirmPayment(jsonBody);
    }
}
