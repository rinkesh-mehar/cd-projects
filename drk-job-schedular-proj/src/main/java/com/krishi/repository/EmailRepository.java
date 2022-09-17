package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Integer> {

	@Query("SELECT e FROM Email e WHERE e.isSent IS NULL AND (e.retry IS NULL OR e.retry < 4)")
	List<Email> findNewEmail();

}
