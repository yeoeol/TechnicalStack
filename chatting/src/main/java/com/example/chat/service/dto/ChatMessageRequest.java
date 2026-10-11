package com.example.chat.service.dto;

public record ChatMessageRequest(
		String roomId,
		Long senderId,
		String content
) {
}
