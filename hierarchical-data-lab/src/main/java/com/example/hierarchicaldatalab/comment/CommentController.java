package com.example.hierarchicaldatalab.comment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

	private final CommentService commentService;

	@GetMapping
	public String commentHome(Model model) {
		List<CommentTreeResponseDto> comments = commentService.getTree();
		model.addAttribute("viewMode", "tree");
		model.addAttribute("viewTitle", "전체 댓글");
		model.addAttribute("treeComments", comments);
		return "comments";
	}

	// 루트 댓글만 조회
	@GetMapping("/roots")
	public String roots(Model model) {
		List<CommentTreeResponseDto> roots = commentService.getTree();
		model.addAttribute("viewMode", "roots");
		model.addAttribute("viewTitle", "루트 댓글");
		model.addAttribute("comments", roots);
		return "comments";
	}

	// 특정 댓글의 모든 하위 댓글 조회
	@GetMapping("/{parentId}/children")
	public String directChildren(@PathVariable("parentId") Long parentId, Model model) {
		List<CommentTreeResponseDto> children = commentService.getChildren(parentId);
		model.addAttribute("viewMode", "children");
		model.addAttribute("viewTitle", parentId + "번 댓글의 하위 댓글");
		model.addAttribute("parentId", parentId);
		model.addAttribute("treeComments", children);
		return "comments";
	}

	// 댓글 생성
	@PostMapping
	public String create(@ModelAttribute CommentRequestDto requestDto) {
		commentService.create(requestDto);
		return "redirect:/comments";
	}

	// 댓글 삭제
	@PostMapping("/{commentId}/delete")
	public String delete(@PathVariable("commentId") Long commentId) {
		commentService.delete(commentId);
		return "redirect:/comments";
	}
}
