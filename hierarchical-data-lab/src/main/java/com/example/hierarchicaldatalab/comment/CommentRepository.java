package com.example.hierarchicaldatalab.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByOrderByCreatedAtAsc();

	@Query(value = """
		SELECT c
		FROM Comment c
		WHERE c.parentId IS NULL
	""")
	List<Comment> findRootComments();

	@Query(value = """
		WITH RECURSIVE descendants AS (
			SELECT c.comment_id, c.parent_id, c.user_id, c.content,
			       c.created_at, c.updated_at, 1 AS depth
			FROM comment c
			WHERE c.parent_id = :parentId

			UNION ALL

			SELECT c.comment_id, c.parent_id, c.user_id, c.content,
			       c.created_at, c.updated_at, d.depth + 1
			FROM comment c
			JOIN descendants d ON c.parent_id = d.comment_id
		)
		SELECT d.comment_id, d.parent_id, d.user_id, d.content,
		       d.created_at, d.updated_at
		FROM descendants d
		ORDER BY d.depth, d.comment_id
	""", nativeQuery = true)
	List<Comment> findChildrenByParent(@Param("parentId") Long parentId);
}
