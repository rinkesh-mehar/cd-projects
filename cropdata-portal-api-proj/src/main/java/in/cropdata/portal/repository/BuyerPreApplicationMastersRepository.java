package in.cropdata.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.model.AgmApplicantType;
import in.cropdata.portal.vo.BuyerPreApplicationMastersVO;
import in.cropdata.portal.vo.CurrencyVo;

@Repository
public interface BuyerPreApplicationMastersRepository extends JpaRepository<AgmApplicantType, Integer> {

	@Query(value = "SELECT ID,Name as Name FROM cdt_master_data.agm_applicant_type", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getApplicantTypeList();

	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agm_application_type where Status = 'Active'", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getApplicationTypeList();
	
	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agm_bussiness_type", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getBusinessTypeList();
	
	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agm_firm_type", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getFirmTypeList();
	
	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agri_commodity", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getCommodityList();
	
	@Query(value = "SELECT ID,CurrencyName as Name,CurrencyCode,CurrencyDecimalCode FROM cdt_master_data.currency_master order by CurrencyCode asc", nativeQuery = true)
	List<CurrencyVo> getCurrencyList();
	
	@Query(value = "SELECT ID,lower(Name) as Name FROM cdt_master_data.agm_designation where status='Active'", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getDesignationList();
	
	@Query(value ="SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agri_commodity order by Name asc", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getActiveCommodityList();

	@Query(value = "SELECT ID as id,Name as name FROM cdt_master_data.agm_applicant_type where ID=?1",nativeQuery = true)
	BuyerPreApplicationMastersVO getApplicatTypeByID(Integer id);
	
}
