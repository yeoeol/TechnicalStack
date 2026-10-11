package com.example.chat.service;

import com.example.chat.domain.ChatMessage;
import com.example.chat.domain.ChatRoom;
import com.example.chat.repository.ChatMessageRepository;
import com.example.chat.repository.ChatRoomRepository;
import com.example.chat.service.dto.ChatMessageRequest;
import com.example.chat.service.dto.ChatMessageResponse;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final SimpMessagingTemplate messagingTemplate;

	public String createRoom(Long member1, Long member2) {
		if (member1.equals(member2)) {
			throw new IllegalArgumentException("동일한 사용자입니다.");
		}

		ChatRoom room = new ChatRoom(List.of(member1, member2));
		return chatRoomRepository.save(room).getId();
	}

	public String createRoom(List<Long> memberIds) {
		ChatRoom room = new ChatRoom(memberIds);
		return chatRoomRepository.save(room).getId();
	}

	public ChatMessageResponse sendMessage(ChatMessageRequest request) {
		ChatRoom room = chatRoomRepository.findById(request.roomId())
				.orElseThrow(() -> new IllegalArgumentException("채팅방 없음"));

		if (!room.getParticipantIds().contains(request.senderId())) {
			throw new IllegalArgumentException("참여자가 아닙니다.");
		}

		ChatMessage chatMessage = new ChatMessage(
				room.getId(),
				request.senderId(),
				request.content()
		);

		ChatMessage saved = chatMessageRepository.save(chatMessage);
		ChatMessageResponse response = ChatMessageResponse.from(saved);

		messagingTemplate.convertAndSend(
				"/topic/chat/" + request.roomId(),
				response
		);

		return response;
	}

	public List<ChatMessageResponse> getMessages(String roomId) {
		return chatMessageRepository
				.findByRoomIdOrderByCreatedAtAscIdAsc(roomId)
				.stream()
				.map(ChatMessageResponse::from)
				.toList();
	}
}
