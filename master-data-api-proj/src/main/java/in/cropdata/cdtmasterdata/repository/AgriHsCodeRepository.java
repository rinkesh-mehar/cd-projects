package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriHsCodeInfDto;
import in.cropdata.cdtmasterdata.model.AgriHsCode;

public interface AgriHsCodeRepository extends JpaRepository<AgriHsCode, Integer> {

	@Query(value = "SELECT AHC.ID,AHC.CommodityID,AHC.GeneralCommodityID,AHC.CommodityClassID,AHC.HSCode,\n" + 
			"			AHC.Description,AHC.UomID,AHC.Status,AHC.CreatedAt,AHC.UpdatedAt,AC.Name as Commodity,\n" + 
			"			AGC.Name as GeneralCommodity,ACC.Name as CommodityClass, GU.Name as Uom FROM agri_hs_code AHC\n" + 
			"			LEFT JOIN agri_commodity AC ON ( AHC.CommodityID = AC.ID)\n" + 
			"			LEFT JOIN agri_general_commodity AGC ON ( AHC.GeneralCommodityID = AGC.ID)\n" + 
			"           LEFT JOIN general_uom GU ON(AHC.UomID = GU.ID)\n" + 
			"			LEFT JOIN agri_commodity_class ACC ON ( AHC.CommodityClassID = ACC.ID)", nativeQuery = true)
	List<AgriHsCodeInfDto> getAgriHsCodeList();

	List<AgriHsCode> findByCommodityId(int commodityId);

	@Query(value = "SELECT AHC.ID,AHC.CommodityID,AHC.GeneralCommodityID,AHC.CommodityClassID,AHC.HSCode, \n" + 
			"						AHC.Description,AHC.UomID,AHC.Status,AC.Name as Commodity, \n" + 
			"						AGC.Name as GeneralCommodity,ACC.Name as CommodityClass, GU.Name as Uom, AHC.IsValid, AHC.ErrorMessage FROM agri_hs_code AHC \n" +
			"						LEFT JOIN agri_commodity AC ON ( AHC.CommodityID = AC.ID) \n" + 
			"						LEFT JOIN agri_general_commodity AGC ON ( AHC.GeneralCommodityID = AGC.ID) \n" + 
			"						LEFT JOIN agri_commodity_class ACC ON ( AHC.CommodityClassID = ACC.ID)\n" + 
			"						LEFT JOIN general_uom GU ON(AHC.UomID = GU.ID) \n" + 
			"						where AHC.HSCode like :searchText\n" + 
			"						OR AHC.HSCode like :searchText\n" + 
			"						OR AC.Name like :searchText\n" + 
			"						OR AGC.Name like :searchText\n" + 
			"						OR ACC.Name like :searchText\n" + 
			"						OR GU.Name like :searchText", 
			countQuery = "SELECT AHC.ID,AHC.CommodityID,AHC.GeneralCommodityID,AHC.CommodityClassID,AHC.HSCode, \n" + 
					"						AHC.Description,AHC.UomID,AHC.Status,AC.Name as Commodity, \n" + 
					"						AGC.Name as GeneralCommodity,ACC.Name as CommodityClass, GU.Name as Uom, AHC.IsValid, AHC.ErrorMessage FROM agri_hs_code AHC \n" +
					"						LEFT JOIN agri_commodity AC ON ( AHC.CommodityID = AC.ID) \n" + 
					"						LEFT JOIN agri_general_commodity AGC ON ( AHC.GeneralCommodityID = AGC.ID) \n" + 
					"						LEFT JOIN agri_commodity_class ACC ON ( AHC.CommodityClassID = ACC.ID)\n" + 
					"						LEFT JOIN general_uom GU ON(AHC.UomID = GU.ID) \n" + 
					"						where AHC.HSCode like :searchText\n" + 
					"						OR AHC.HSCode like :searchText\n" + 
					"						OR AC.Name like :searchText\n" + 
					"						OR AGC.Name like :searchText\n" + 
					"						OR ACC.Name like :searchText\n" + 
					"						OR GU.Name like :searchText", nativeQuery = true)
	Page<AgriHsCodeInfDto> getPageAgriHsCodeList(Pageable pageable, String searchText);
	
	@Query(value = "SELECT AHC.ID,AHC.CommodityID,AHC.GeneralCommodityID,AHC.CommodityClassID,AHC.HSCode, \n" + 
			"						AHC.Description,AHC.UomID,AHC.Status,AC.Name as Commodity, \n" + 
			"						AGC.Name as GeneralCommodity,ACC.Name as CommodityClass, GU.Name as Uom, AHC.IsValid FROM agri_hs_code_missing AHC \n" +
			"						LEFT JOIN agri_commodity AC ON ( AHC.CommodityID = AC.ID) \n" + 
			"						LEFT JOIN agri_general_commodity AGC ON ( AHC.GeneralCommodityID = AGC.ID) \n" + 
			"						LEFT JOIN agri_commodity_class ACC ON ( AHC.CommodityClassID = ACC.ID)\n" + 
			"						LEFT JOIN general_uom GU ON(AHC.UomID = GU.ID) \n" + 
			"						where AHC.HSCode like :searchText\n" + 
			"						OR AHC.HSCode like :searchText\n" + 
			"						OR AC.Name like :searchText\n" + 
			"						OR AGC.Name like :searchText\n" + 
			"						OR ACC.Name like :searchText\n" + 
			"						OR GU.Name like :searchText", 
			countQuery = "SELECT AHC.ID,AHC.CommodityID,AHC.GeneralCommodityID,AHC.CommodityClassID,AHC.HSCode, \n" + 
					"						AHC.Description,AHC.UomID,AHC.Status,AC.Name as Commodity, \n" + 
					"						AGC.Name as GeneralCommodity,ACC.Name as CommodityClass, GU.Name as Uom, AHC.IsValid FROM agri_hs_code_missing AHC \n" +
					"						LEFT JOIN agri_commodity AC ON ( AHC.CommodityID = AC.ID) \n" + 
					"						LEFT JOIN agri_general_commodity AGC ON ( AHC.GeneralCommodityID = AGC.ID) \n" + 
					"						LEFT JOIN agri_commodity_class ACC ON ( AHC.CommodityClassID = ACC.ID)\n" + 
					"						LEFT JOIN general_uom GU ON(AHC.UomID = GU.ID) \n" + 
					"						where AHC.HSCode like :searchText\n" + 
					"						OR AHC.HSCode like :searchText\n" + 
					"						OR AC.Name like :searchText\n" + 
					"						OR AGC.Name like :searchText\n" + 
					"						OR ACC.Name like :searchText\n" + 
					"						OR GU.Name like :searchText", nativeQuery = true)
	Page<AgriHsCodeInfDto> getPageAgriHsCodeMissingList(Pageable pageable, String searchText);

	@Query(value = "SELECT AHC.ID,AHC.CommodityID,AHC.GeneralCommodityID,AHC.CommodityClassID,AHC.HSCode, \n" +
			"						AHC.Description,AHC.UomID,AHC.Status,AC.Name as Commodity, \n" +
			"						AGC.Name as GeneralCommodity,ACC.Name as CommodityClass, GU.Name as Uom, AHC.IsValid, AHC.ErrorMessage FROM agri_hs_code AHC \n" +
			"						LEFT JOIN agri_commodity AC ON ( AHC.CommodityID = AC.ID) \n" +
			"						LEFT JOIN agri_general_commodity AGC ON ( AHC.GeneralCommodityID = AGC.ID) \n" +
			"						LEFT JOIN agri_commodity_class ACC ON ( AHC.CommodityClassID = ACC.ID)\n" +
			"						LEFT JOIN general_uom GU ON(AHC.UomID = GU.ID) \n" +
			"						where AHC.IsValid = 0 and (AHC.HSCode like :searchText\n" +
			"						OR AHC.HSCode like :searchText\n" +
			"						OR AC.Name like :searchText\n" +
			"						OR AGC.Name like :searchText\n" +
			"						OR ACC.Name like :searchText\n" +
			"						OR GU.Name like :searchText)",
			countQuery = "SELECT AHC.ID,AHC.CommodityID,AHC.GeneralCommodityID,AHC.CommodityClassID,AHC.HSCode, \n" +
					"						AHC.Description,AHC.UomID,AHC.Status,AC.Name as Commodity, \n" +
					"						AGC.Name as GeneralCommodity,ACC.Name as CommodityClass, GU.Name as Uom, AHC.IsValid, AHC.ErrorMessage FROM agri_hs_code AHC \n" +
					"						LEFT JOIN agri_commodity AC ON ( AHC.CommodityID = AC.ID) \n" +
					"						LEFT JOIN agri_general_commodity AGC ON ( AHC.GeneralCommodityID = AGC.ID) \n" +
					"						LEFT JOIN agri_commodity_class ACC ON ( AHC.CommodityClassID = ACC.ID)\n" +
					"						LEFT JOIN general_uom GU ON(AHC.UomID = GU.ID) \n" +
					"						where AHC.IsValid = 0 and (AHC.HSCode like :searchText\n" +
					"						OR AHC.HSCode like :searchText\n" +
					"						OR AC.Name like :searchText\n" +
					"						OR AGC.Name like :searchText\n" +
					"						OR ACC.Name like :searchText\n" +
					"						OR GU.Name like :searchText)", nativeQuery = true)
	Page<AgriHsCodeInfDto> getPageAgriHsCodeListInvalidated(Pageable pageable, String searchText);
	
	@Query(value = "SELECT AHC.ID,AHC.CommodityID,AHC.GeneralCommodityID,AHC.CommodityClassID,AHC.HSCode, \n" +
			"						AHC.Description,AHC.UomID,AHC.Status,AC.Name as Commodity, \n" +
			"						AGC.Name as GeneralCommodity,ACC.Name as CommodityClass, GU.Name as Uom, AHC.IsValid FROM agri_hs_code_missing AHC \n" +
			"						LEFT JOIN agri_commodity AC ON ( AHC.CommodityID = AC.ID) \n" +
			"						LEFT JOIN agri_general_commodity AGC ON ( AHC.GeneralCommodityID = AGC.ID) \n" +
			"						LEFT JOIN agri_commodity_class ACC ON ( AHC.CommodityClassID = ACC.ID)\n" +
			"						LEFT JOIN general_uom GU ON(AHC.UomID = GU.ID) \n" +
			"						where AHC.IsValid = 0 and (AHC.HSCode like :searchText\n" +
			"						OR AHC.HSCode like :searchText\n" +
			"						OR AC.Name like :searchText\n" +
			"						OR AGC.Name like :searchText\n" +
			"						OR ACC.Name like :searchText\n" +
			"						OR GU.Name like :searchText)",
			countQuery = "SELECT AHC.ID,AHC.CommodityID,AHC.GeneralCommodityID,AHC.CommodityClassID,AHC.HSCode, \n" +
					"						AHC.Description,AHC.UomID,AHC.Status,AC.Name as Commodity, \n" +
					"						AGC.Name as GeneralCommodity,ACC.Name as CommodityClass, GU.Name as Uom, AHC.IsValid FROM agri_hs_code_missing AHC \n" +
					"						LEFT JOIN agri_commodity AC ON ( AHC.CommodityID = AC.ID) \n" +
					"						LEFT JOIN agri_general_commodity AGC ON ( AHC.GeneralCommodityID = AGC.ID) \n" +
					"						LEFT JOIN agri_commodity_class ACC ON ( AHC.CommodityClassID = ACC.ID)\n" +
					"						LEFT JOIN general_uom GU ON(AHC.UomID = GU.ID) \n" +
					"						where AHC.IsValid = 0 and (AHC.HSCode like :searchText\n" +
					"						OR AHC.HSCode like :searchText\n" +
					"						OR AC.Name like :searchText\n" +
					"						OR AGC.Name like :searchText\n" +
					"						OR ACC.Name like :searchText\n" +
					"						OR GU.Name like :searchText)", nativeQuery = true)
	Page<AgriHsCodeInfDto> getPageAgriHsCodeMissingListInvalidated(Pageable pageable, String searchText);

}
