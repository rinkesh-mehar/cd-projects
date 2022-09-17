package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.DrKrishiUsers;

public interface DrKrishiUsersRepository extends JpaRepository<DrKrishiUsers, Integer>{

	List<DrKrishiUsers> findByStatus(Integer status);

}
