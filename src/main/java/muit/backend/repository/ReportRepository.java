package muit.backend.repository;

import muit.backend.domain.entity.member.Report;
import muit.backend.domain.enums.ReportObjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    Page<Report> findAllByReportObjectType(ReportObjectType reportObjectType, PageRequest of);

    Page<Report> findAllByReportObjectTypeIn(ReportObjectType[] types, PageRequest of);
}

