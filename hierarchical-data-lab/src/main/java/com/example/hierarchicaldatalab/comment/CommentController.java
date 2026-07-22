package com.example.hierarchicaldatalab.comment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

	private final CommentService commentService;

	@GetMapping
	public String commentHome() {
		return "comments";
	}

	// 댓글 생성
	@PostMapping
	public String create(@ModelAttribute CommentRequestDto requestDto) {
		commentService.create(requestDto);
		return "redirect:/";
	}
}
