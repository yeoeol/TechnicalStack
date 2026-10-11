package com.example.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatPageController {

	@GetMapping("/chat/{roomId}")
	public String chatPage(
			@PathVariable String roomId,
			@RequestParam Long memberId,
			Model model
	) {
		model.addAttribute("roomId", roomId);
		model.addAttribute("memberId", memberId);

		return "chat";
	}
}
