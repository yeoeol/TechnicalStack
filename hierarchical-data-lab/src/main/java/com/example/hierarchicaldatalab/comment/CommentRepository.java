package com.example.hierarchicaldatalab.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query(value = """
		SELECT c
		FROM Comment c
		WHERE c.parentId IS NULL
	""")
	List<Comment> findRootComments();

	@Query(value = """
		SELECT c
		FROM Comment c
		WHERE NOT EXISTS (
			SELECT 1
			FROM Comment child
			WHERE child.parentId = c.id
		)
	""")
	List<Comment> findBottomComments();
}
