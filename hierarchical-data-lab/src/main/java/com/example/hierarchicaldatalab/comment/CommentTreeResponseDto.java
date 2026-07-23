package com.example.hierarchicaldatalab.comment;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CommentTreeResponseDto {

	private Long commentId;
	private Long parentId;
	private Long userId;
	private String content;
	private boolean deleted;
	private int descendantCount;
	private final List<CommentTreeResponseDto> children;

	public CommentTreeResponseDto(
			Long commentId,
			Long parentId,
			Long userId,
			String content,
			boolean deleted,
			int descendantCount,
			List<CommentTreeResponseDto> children
	) {
		this.commentId = commentId;
		this.parentId = parentId;
		this.userId = userId;
		this.content = content;
		this.deleted = deleted;
		this.descendantCount = descendantCount;
		this.children = children;
	}

	public static CommentTreeResponseDto init(Comment comment) {
		return new CommentTreeResponseDto(
				comment.getId(),
				comment.getParentId(),
				comment.getUserId(),
				comment.isDeleted()
					? "삭제된 댓글입니다."
					: comment.getContent(),
				comment.isDeleted(),
				0,
				new ArrayList<>()
		);
	}

	public void addChild(CommentTreeResponseDto child) {
		children.add(child);
	}

	public boolean pruneDeletedBranches() {
		children.removeIf(child -> !child.pruneDeletedBranches());
		descendantCount = children.stream()
				.mapToInt(child -> 1 + child.descendantCount)
				.sum();

		return !deleted || !children.isEmpty();
	}
}
