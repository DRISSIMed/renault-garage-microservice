package com.cap.renault.repository;

import com.cap.renault.entity.OpeningTimeEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpeningTimeEntryRepository extends JpaRepository<OpeningTimeEntry, Long> {
    List<OpeningTimeEntry> findByGarageId(Long garageId);
}
