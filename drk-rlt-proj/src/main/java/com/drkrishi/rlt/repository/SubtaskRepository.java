package com.drkrishi.rlt.repository;

import com.drkrishi.rlt.entity.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubtaskRepository extends JpaRepository<Subtask, String> {

    Optional<Subtask> findFirstByTaskIdOrderByCreatedAtDesc(String taskId);

}
