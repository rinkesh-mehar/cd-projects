package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriAgrochemicalBrandInfDto;
import in.cropdata.cdtmasterdata.model.AgriAgrochemicalBrand;

public interface AgriAgrochemicalBrandRepository extends JpaRepository<AgriAgrochemicalBrand, Integer> {
	
	@Query(value = "SELECT AAB.ID,AAB.Name,AAB.CompanyID,AAB.AgrochemicalID,AAB.AgrochemicalStatus,AAB.Status,AAB.UpdatedAt,AAB.CreatedAt, GC.Name as Company,\n" + 
			"			AA.Name as Agrochemical, AAB.IsValid, AAB.ErrorMessage FROM agri_agrochemical_brand AAB \n" +
			"			Left Join general_company GC on (AAB.CompanyID = GC.ID) \n" + 
			"			Left join agri_commodity_agrochemical AA on (AAB.AgrochemicalID = AA.ID)\n" +
			"			where AAB.Name like :searchText\n" + 
			"			OR AA.Name like :searchText\n" + 
			"			OR GC.Name like :searchText\n" + 
			"			OR AAB.Name like :searchText",
			countQuery = "SELECT AAB.ID,AAB.Name,AAB.CompanyID,AAB.AgrochemicalID,AAB.AgrochemicalStatus,AAB.Status,AAB.UpdatedAt,AAB.CreatedAt, GC.Name as Company,\n" + 
					"			AA.Name as Agrochemical, AAB.IsValid, AAB.ErrorMessage FROM agri_agrochemical_brand AAB \n" +
					"			Left Join general_company GC on (AAB.CompanyID = GC.ID) \n" + 
					"			Left join agri_commodity_agrochemical AA on (AAB.AgrochemicalID = AA.ID)\n" +
					"			where AAB.Name like :searchText\n" + 
					"			OR AA.Name like :searchText\n" + 
					"			OR GC.Name like :searchText\n" + 
					"			OR AAB.Name like :searchText", nativeQuery  = true)
	Page<AgriAgrochemicalBrandInfDto> getAllAgrochemicalBrand(Pageable pageable, String searchText);
	
	@Query(value = "SELECT AAB.ID,AAB.Name,AAB.CompanyID,AAB.AgrochemicalID,AAB.AgrochemicalStatus,AAB.Status,AAB.UpdatedAt,AAB.CreatedAt, GC.Name as Company,\n" + 
			"			AA.Name as Agrochemical FROM agri_agrochemical_brand_missing AAB \n" +
			"			Left Join general_company GC on (AAB.CompanyID = GC.ID) \n" + 
			"			Left join agri_commodity_agrochemical AA on (AAB.AgrochemicalID = AA.ID)\n" +
			"			where AAB.Name like :searchText\n" + 
			"			OR AA.Name like :searchText\n" + 
			"			OR GC.Name like :searchText\n" + 
			"			OR AAB.Name like :searchText",
			countQuery = "SELECT AAB.ID,AAB.Name,AAB.CompanyID,AAB.AgrochemicalID,AAB.AgrochemicalStatus,AAB.Status,AAB.UpdatedAt,AAB.CreatedAt, GC.Name as Company,\n" + 
					"			AA.Name as Agrochemical FROM agri_agrochemical_brand_missing AAB \n" +
					"			Left Join general_company GC on (AAB.CompanyID = GC.ID) \n" + 
					"			Left join agri_commodity_agrochemical AA on (AAB.AgrochemicalID = AA.ID)\n" +
					"			where AAB.Name like :searchText\n" + 
					"			OR AA.Name like :searchText\n" + 
					"			OR GC.Name like :searchText\n" + 
					"			OR AAB.Name like :searchText", nativeQuery  = true)
	Page<AgriAgrochemicalBrandInfDto> getAllAgrochemicalBrandMissing(Pageable pageable, String searchText);

	@Query(value = "SELECT AAB.ID,AAB.Name,AAB.CompanyID,AAB.AgrochemicalID,AAB.AgrochemicalStatus,AAB.Status,AAB.UpdatedAt,AAB.CreatedAt, GC.Name as Company,\n" +
			"			AA.Name as Agrochemical, AAB.ErrorMessage FROM agri_agrochemical_brand AAB \n" +
			"			Left Join general_company GC on (AAB.CompanyID = GC.ID) \n" +
			"			Left join agri_commodity_agrochemical AA on (AAB.AgrochemicalID = AA.ID)\n" +
			"			where AAB.IsValid = 0 and (AAB.Name like :searchText\n" +
			"			OR AA.Name like :searchText\n" +
			"			OR GC.Name like :searchText\n" +
			"			OR AAB.Name like :searchText)",
			countQuery = "SELECT AAB.ID,AAB.Name,AAB.CompanyID,AAB.AgrochemicalID,AAB.AgrochemicalStatus,AAB.Status,AAB.UpdatedAt,AAB.CreatedAt, GC.Name as Company,\n" +
					"			AA.Name as Agrochemical, AAB.ErrorMessage FROM agri_agrochemical_brand AAB \n" +
					"			Left Join general_company GC on (AAB.CompanyID = GC.ID) \n" +
					"			Left join agri_commodity_agrochemical AA on (AAB.AgrochemicalID = AA.ID)\n" +
					"			where AAB.IsValid = 0 and (AAB.Name like :searchText\n" +
					"			OR AA.Name like :searchText\n" +
					"			OR GC.Name like :searchText\n" +
					"			OR AAB.Name like :searchText)", nativeQuery  = true)
	Page<AgriAgrochemicalBrandInfDto> getAllAgrochemicalBrandInvalidated(Pageable pageable, String searchText);
	
	@Query(value = "SELECT AAB.ID,AAB.Name,AAB.CompanyID,AAB.AgrochemicalID,AAB.AgrochemicalStatus,AAB.Status,AAB.UpdatedAt,AAB.CreatedAt, GC.Name as Company,\n" +
			"			AA.Name as Agrochemical FROM agri_agrochemical_brand_missing AAB \n" +
			"			Left Join general_company GC on (AAB.CompanyID = GC.ID) \n" +
			"			Left join agri_commodity_agrochemical AA on (AAB.AgrochemicalID = AA.ID)\n" +
			"			where AAB.IsValid = 0 and (AAB.Name like :searchText\n" +
			"			OR AA.Name like :searchText\n" +
			"			OR GC.Name like :searchText\n" +
			"			OR AAB.Name like :searchText)",
			countQuery = "SELECT AAB.ID,AAB.Name,AAB.CompanyID,AAB.AgrochemicalID,AAB.AgrochemicalStatus,AAB.Status,AAB.UpdatedAt,AAB.CreatedAt, GC.Name as Company,\n" +
					"			AA.Name as Agrochemical FROM agri_agrochemical_brand_missing AAB \n" +
					"			Left Join general_company GC on (AAB.CompanyID = GC.ID) \n" +
					"			Left join agri_commodity_agrochemical AA on (AAB.AgrochemicalID = AA.ID)\n" +
					"			where AAB.IsValid = 0 and (AAB.Name like :searchText\n" +
					"			OR AA.Name like :searchText\n" +
					"			OR GC.Name like :searchText\n" +
					"			OR AAB.Name like :searchText)", nativeQuery  = true)
	Page<AgriAgrochemicalBrandInfDto> getAllAgrochemicalBrandMissingInvalidated(Pageable pageable, String searchText);
}
