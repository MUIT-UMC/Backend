package muit.backend.repository;

import muit.backend.domain.entity.member.Inquiry;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.enums.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByPostType(PostType postType, PageRequest pageRequest);

    Page<Post> findAllByMemberId(Long memberId, PageRequest pageRequest);
}
