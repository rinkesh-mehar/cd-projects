package in.cropdata.farmers.app.drk.repository;

import in.cropdata.farmers.app.DTO.CaseCropInfoDTO;
import in.cropdata.farmers.app.drk.model.FarmerCaseKMLPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author RinkeshKM
 * @Date 12/03/21
 */
public interface FarmerCaseKMLPointRepository extends JpaRepository<FarmerCaseKMLPoint, Integer> {

    @Query(value = "select latitude, longitude from farmer_case_kmlpoints where farm_id = ?1 and case_id = ?2", nativeQuery = true)
    List<CaseCropInfoDTO> findAllByFarmIdAndCaseID(String farmID, String caseID);

}
