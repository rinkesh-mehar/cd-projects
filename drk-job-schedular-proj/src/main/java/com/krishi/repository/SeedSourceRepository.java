package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

//import com.krishi.entity.Season;
import com.krishi.entity.SeedSource;

public interface SeedSourceRepository extends JpaRepository<SeedSource, Integer>,QueryByExampleExecutor<SeedSource>{

}
