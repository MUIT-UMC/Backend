package muit.backend.repository;

import muit.backend.domain.entity.member.InquiryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryResponseRepository extends JpaRepository<InquiryResponse, Long> {

}
