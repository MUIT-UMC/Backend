package muit.backend.repository;

import muit.backend.domain.entity.member.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 검색어가 있을 때
    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.member m " +
            "JOIN FETCH r.memberTicketList mt " +
            "JOIN FETCH mt.amateurTicket at " +
            "JOIN FETCH at.amateurShow ams " +
            "WHERE m.name LIKE CONCAT('%', :keyword, '%') " +
            "OR ams.name LIKE CONCAT('%', :keyword, '%') " +
            "OR ams.place LIKE CONCAT('%', :keyword, '%') " +
            "OR ams.schedule LIKE CONCAT('%', :keyword, '%')")
    Page<Reservation> findByKeyword(Pageable pageable, @Param("keyword") String keyword);
}