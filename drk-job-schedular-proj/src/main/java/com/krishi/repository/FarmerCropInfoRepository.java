package com.krishi.repository;

import com.krishi.entity.FarmerCropInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FarmerCropInfoRepository extends JpaRepository<FarmerCropInfo, String> {

    @Query(value = "select * from farmer_crop_info where id =?1", nativeQuery = true)
    FarmerCropInfo getFarmerCropInfoById(String farmerCropInfoId);
}
