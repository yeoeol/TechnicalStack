package com.example.chat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "chat_messages")
@CompoundIndex(
		name = "idx_room_created_id",
		def = "{'roomId': 1, 'createdAt': -1, '_id': -1}"
)
public class ChatMessage {

	@Id
	private String id;

	private String roomId;

	private Long senderId;

	private String content;

	private LocalDateTime createdAt;

	public ChatMessage(
			String roomId,
			Long senderId,
			String content
	) {
		this.roomId = roomId;
		this.senderId = senderId;
		this.content = content;
		this.createdAt = LocalDateTime.now();
	}
}
