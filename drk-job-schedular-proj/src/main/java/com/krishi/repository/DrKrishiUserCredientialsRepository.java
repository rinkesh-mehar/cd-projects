package com.krishi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.DrKrishiUserCredientials;

public interface DrKrishiUserCredientialsRepository extends JpaRepository<DrKrishiUserCredientials, Integer>{

	List<DrKrishiUserCredientials> findByPasswordResetDateBetween(Date date, Date date2);

	@Query("select c from DrKrishiUserCredientials c where c.isForcedPwdChange=0 and c.passwordResetDate < :date")
	List<DrKrishiUserCredientials> findByPasswordResetDate(@Param("date") Date date);

	DrKrishiUserCredientials findByUserId(Integer userId);

}
