package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.DrKrishiUsers;
import com.krishi.entity.ProxyRelationType;

public interface ProxyRelationTypeRepository extends JpaRepository<ProxyRelationType, Integer> {

}
