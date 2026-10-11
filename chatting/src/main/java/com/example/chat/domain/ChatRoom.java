package com.example.chat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "chat_rooms")
public class ChatRoom {

	@Id
	private String id;

	private List<Long> participantIds;

	private LocalDateTime createdAt;

	public ChatRoom(List<Long> participantIds) {
		this.participantIds = participantIds;
		this.createdAt = LocalDateTime.now();
	}
}
