package com.example.chat.service.dto;

import com.example.chat.domain.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageResponse(
		String id,
		String roomId,
		Long senderId,
		String content,
		LocalDateTime createdAt
) {
	public static ChatMessageResponse from(ChatMessage chatMessage) {
		return new ChatMessageResponse(
				chatMessage.getId(),
				chatMessage.getRoomId(),
				chatMessage.getSenderId(),
				chatMessage.getContent(),
				chatMessage.getCreatedAt()
		);
	}
}
