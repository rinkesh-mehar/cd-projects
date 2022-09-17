package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.CaseNdvi;

public interface CaseNdviRepository extends JpaRepository<CaseNdvi, Integer>{

	List<CaseNdvi> findByMonthAndYearOrMonthAndYear(int lastMonth, int lastMonthYear, int lastToPreviousMonth,
			int lastToPreviousMonthYear);

}
