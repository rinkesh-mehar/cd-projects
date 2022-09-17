package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.FarmCase;

public interface FarmCaseRepository extends JpaRepository<FarmCase, String> {

}
