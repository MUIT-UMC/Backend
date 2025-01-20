package muit.backend.repository;

import muit.backend.domain.entity.member.Comment;
import muit.backend.domain.entity.member.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPost(Post post, PageRequest pageRequest);
}
