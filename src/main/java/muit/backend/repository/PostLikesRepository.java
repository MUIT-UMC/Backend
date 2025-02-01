package muit.backend.repository;

import muit.backend.domain.entity.member.Member;
import muit.backend.domain.entity.member.Post;
import muit.backend.domain.entity.member.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Integer> {
    PostLikes findByMemberAndPost(Member member, Post post);
}
