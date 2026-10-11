package com.example.chat.controller;

import com.example.chat.service.ChatService;
import com.example.chat.service.dto.ChatMessageResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

	private final ChatService chatService;

	@PostMapping("/rooms")
	public String createRoom(
			@RequestParam Long member1,
			@RequestParam Long member2
	) {
		return chatService.createRoom(member1, member2);
	}

	@PostMapping("/rooms/group")
	public String createRoom(
			@RequestParam List<Long> memberIds
	) {
		return chatService.createRoom(memberIds);
	}

	@GetMapping("/rooms/{roomId}/messages")
	public List<ChatMessageResponse> getMessages(
			@PathVariable String roomId
	) {
		return chatService.getMessages(roomId);
	}
}
