package muit.backend.repository;

import muit.backend.domain.entity.coProduct.CoProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoProductRepository extends JpaRepository<CoProduct, Long> {

    Optional<CoProduct> findByScheduleId(Long scheduleId);
}
