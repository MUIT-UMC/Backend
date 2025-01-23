package muit.backend.repository;

import muit.backend.domain.entity.member.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
