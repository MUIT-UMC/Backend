package muit.backend.repository;

import muit.backend.domain.entity.coProduct.Schedule;
import muit.backend.domain.enums.OpenStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
//    Optional<List<Schedule>> findAllByOpenStatus(OpenStatus openStatus);
}
