package com.example.pgtest.payment.controller;

import com.example.pgtest.payment.config.PaymentProperties;
import com.example.pgtest.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentProperties paymentProperties;
    private final PaymentService paymentService;

    @GetMapping("/payments")
    public String paymentPage(@RequestParam Long userId,
                              @RequestParam int amount,
                              Model model
    ) {
        String orderId = UUID.randomUUID().toString().substring(0, 12);

        model.addAttribute("userId", userId);
        model.addAttribute("clientKey", paymentProperties.getClientKey());
        model.addAttribute("amount", amount);
        model.addAttribute("orderId", orderId);

        paymentService.createPayment(orderId, userId, amount);
        return "payment";
    }

    @GetMapping("/payments/success")
    public String successPayment(
            @RequestParam String orderId,
            @RequestParam String paymentKey,
            @RequestParam int amount,
            Model model
    ) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("amount", amount);

        return "success";
    }
}
