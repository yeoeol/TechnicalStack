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

	public int calculateDescendantCount() {
		int count = 0;

		for (CommentTreeResponseDto child : children) {
			count += 1 + child.calculateDescendantCount();
		}

		descendantCount = count;
		return count;
	}
}
