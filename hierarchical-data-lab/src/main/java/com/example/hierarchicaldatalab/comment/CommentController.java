package com.example.hierarchicaldatalab.comment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

	// 루트 댓글만 조회
	@GetMapping("/root")
	public String root(Model model) {
		List<CommentResponseDto> roots = commentService.getRoots();
		model.addAttribute(roots);
		return "comments";
	}

	// 최하위 댓글만 조회
	@GetMapping("/bottom")
	public String bottom(Model model) {
		List<CommentResponseDto> bottoms = commentService.getBottoms();
		model.addAttribute(bottoms);
		return "comments";
	}

	// 댓글 생성
	@PostMapping
	public String create(@ModelAttribute CommentRequestDto requestDto) {
		commentService.create(requestDto);
		return "redirect:/";
	}
}
