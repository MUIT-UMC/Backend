package muit.backend.repository;


import muit.backend.domain.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    // 검색어가 있을 때
    @Query("SELECT m FROM Member m WHERE m.username LIKE %:keyword% " +
            "OR m.name LIKE %:keyword% " +
            "OR m.email LIKE %:keyword% " +
            "OR m.phone LIKE %:keyword%")
    Page<Member> findByKeyword(Pageable pageable, @Param("keyword") String keyword);

    @Query("select m from Member m where m.email = :email")
    Optional<Member> findMemberByEmail(@Param("email") String email);
}
