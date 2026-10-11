package com.example.chat.repository;

import com.example.chat.domain.ChatMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
	List<ChatMessage> findByRoomIdOrderByCreatedAtAscIdAsc(
			String roomId
	);

	Page<ChatMessage> findByRoomId(
			String roomId,
			Pageable pageable
	);

	long countByRoomId(String roomId);
}
