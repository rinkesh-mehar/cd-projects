package com.krishi.repository;

import com.krishi.entity.JobSyncDetails;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSyncDetailsRepository extends JpaRepository<JobSyncDetails, Integer> {

    List<JobSyncDetails> findByprocessedDateNull(Pageable pageable);

	List<JobSyncDetails> findByprocessedDateNull();
}
