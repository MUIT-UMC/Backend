package muit.backend.repository;

import muit.backend.domain.entity.member.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    // 전체 조회용 (member 함께 조회)
    @Query("SELECT i FROM Inquiry i JOIN FETCH i.member ORDER BY i.createdAt DESC")
    Page<Inquiry> findAllWithMember(Pageable pageable);

    // 검색어가 있을 때
    @Query("SELECT i FROM Inquiry i JOIN FETCH i.member WHERE " +
            "i.member.username LIKE %:keyword% " +
            "OR i.member.name LIKE %:keyword% " +
            "OR i.member.email LIKE %:keyword% " +
            "OR i.member.phone LIKE %:keyword% " +
            "ORDER BY i.createdAt DESC")
    Page<Inquiry> findByKeyword(Pageable pageable, @Param("keyword") String keyword);

    // 특정 문의 조회용 (member 함께 조회)
    @Query("SELECT DISTINCT i FROM Inquiry i " +
            "JOIN FETCH i.member " +
            "LEFT JOIN FETCH i.inquiryResponse " +  // 답변은 항상 존재하지 X -> LEFT JOIN 사용
            "WHERE i.id = :inquiryId")
    Optional<Inquiry> findByIdWithMemberAndResponse(@Param("inquiryId") Long inquiryId);
}
