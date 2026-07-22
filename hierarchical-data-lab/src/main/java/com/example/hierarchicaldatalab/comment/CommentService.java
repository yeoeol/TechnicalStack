package com.example.hierarchicaldatalab.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	@Transactional
	public void create(CommentRequestDto requestDto) {
		commentRepository.save(
				Comment.create(
						requestDto.parentId(),
						requestDto.userId(),
						requestDto.content()
				)
		);
	}

	@Transactional(readOnly = true)
	public List<CommentResponseDto> getRoots() {
		List<Comment> roots = commentRepository.findRootComments();
		return roots.stream()
				.map(CommentResponseDto::from)
				.toList();
	}

	@Transactional(readOnly = true)
	public List<CommentResponseDto> getBottoms() {
		List<Comment> bottoms = commentRepository.findBottomComments();
		return bottoms.stream()
				.map(CommentResponseDto::from)
				.toList();
	}
}
