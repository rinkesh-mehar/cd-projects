package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.TileZL11;

public interface TileZL11Repository extends JpaRepository<TileZL11, Long>, QueryByExampleExecutor<TileZL11> {
}
