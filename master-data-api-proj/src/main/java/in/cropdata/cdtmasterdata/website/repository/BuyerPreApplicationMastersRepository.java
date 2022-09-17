package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.website.model.AgmApplicantType;
import in.cropdata.cdtmasterdata.website.model.vo.BuyerPreApplicationMastersVO;
import in.cropdata.cdtmasterdata.website.model.vo.CurrencyVo;

@Repository
public interface BuyerPreApplicationMastersRepository extends JpaRepository<AgmApplicantType, Integer> {

	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agm_applicant_type order by Name", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getApplicantTypeList();
	
	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agm_application_type order by Name", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getApplicationTypeList();
	
	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agm_bussiness_type order by Name", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getBusinessTypeList();
	
	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agm_firm_type order by Name", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getFirmTypeList();
	
	@Query(value = "SELECT ID,LOWER(Name) as Name FROM cdt_master_data.agri_commodity order by Name", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getCommodityList();
	
	@Query(value = "SELECT ID,CurrencyName as Name,concat(CurrencyCode,'-',CurrencyName) as CurrencyCode,CurrencyDecimalCode FROM cdt_master_data.currency_master order by CurrencyName", nativeQuery = true)
	List<CurrencyVo> getCurrencyList();
	
	@Query(value = "SELECT ID,lower(Name) as Name FROM cdt_master_data.agm_designation order by Name", nativeQuery = true)
	List<BuyerPreApplicationMastersVO> getDesignationList();
	
}
