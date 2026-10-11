package com.example.chat.repository;

import com.example.chat.domain.ChatRoom;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
	List<ChatRoom> findByParticipantIdsContaining(Long memberId);
}
