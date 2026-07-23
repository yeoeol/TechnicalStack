package com.example.hierarchicaldatalab.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	public List<CommentTreeResponseDto> getTree() {
		return buildTree(commentRepository.findAllByOrderByCreatedAtAsc());
	}

	@Transactional(readOnly = true)
	public List<CommentTreeResponseDto> getChildren(Long parentId) {
		List<Comment> children = commentRepository.findChildrenByParent(parentId);
		return buildTree(children);
	}

	private List<CommentTreeResponseDto> buildTree(List<Comment> comments) {
		Map<Long, CommentTreeResponseDto> commentsById = new LinkedHashMap<>();
		for (Comment comment : comments) {
			commentsById.put(comment.getId(), CommentTreeResponseDto.init(comment));
		}

		List<CommentTreeResponseDto> roots = new ArrayList<>();
		for (Comment comment : comments) {
			CommentTreeResponseDto current = commentsById.get(comment.getId());
			CommentTreeResponseDto parent = commentsById.get(comment.getParentId());

			if (parent == null) {
				roots.add(current);
				continue;
			}

			parent.addChild(current);
		}

		for (CommentTreeResponseDto root : roots) {
			root.calculateDescendantCount();
		}

		return roots;
	}
}
