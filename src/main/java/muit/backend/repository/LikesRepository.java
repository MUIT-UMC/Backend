package muit.backend.repository;

import muit.backend.domain.entity.member.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllByMemberId(Long memberId);

    Likes findByMemberIdAndMusicalId(Long memberId, Long musicalId);

    void deleteByMemberIdAndMusicalId(Long memberId, Long musicalId);
}
