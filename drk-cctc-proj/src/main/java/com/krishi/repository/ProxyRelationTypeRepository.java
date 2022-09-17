package com.krishi.repository;

import com.krishi.entity.ProxyRelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProxyRelationTypeRepository extends JpaRepository<ProxyRelationType, Integer>{

	List<ProxyRelationType> findByStatus(int statusId);
}
