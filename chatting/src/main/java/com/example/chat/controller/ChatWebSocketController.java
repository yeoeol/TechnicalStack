package com.example.chat.controller;

import com.example.chat.service.ChatService;
import com.example.chat.service.dto.ChatMessageRequest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

	private final ChatService chatService;

	@MessageMapping("/chat/send")
	public void sendMessage(ChatMessageRequest request) {
		chatService.sendMessage(request);
	}
}
