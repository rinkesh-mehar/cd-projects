package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.Sms;

public interface SmsRepository extends JpaRepository<Sms, Integer>{

	@Query("SELECT s FROM Sms s WHERE s.status=0 AND (s.isSent IS NULL OR s.isSent = false) AND (s.retry IS NULL OR s.retry < 4)")
	List<Sms> findByNewSms();

}
