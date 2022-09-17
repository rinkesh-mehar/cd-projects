/**
 * 
 */
package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.website.model.PartnershipRequest;
import in.cropdata.cdtmasterdata.website.model.vo.PartnershipRequestVO;

/**
 * @author cropdata-kunal
 *
 */
public interface PartnershipRequestRepository extends JpaRepository<PartnershipRequest, Integer> {

	@Query(value = "SELECT ID,Name FROM cdt_master_data.agm_industries order by ID", nativeQuery = true)
	CopyOnWriteArrayList<Map<String, Object>> getAllIndustries();

	@Query(value = "SELECT ID,Name FROM cdt_master_data.agm_partner_programs  order by ID", nativeQuery = true)
	List<Map<String, Object>> getPartnershipProgram();
	
	@Query(value = "SELECT partnership_request.ID,\n" + 
			"			    UPPER(partnership_request.Name) as Name,\n" + 
			"			    partnership_request.Email,\n" + 
			"			    partnership_request.Mobile,\n" + 
			"			    UPPER(partnership_request.OrganisationName) as OrganisationName,\n" + 
			"                DATE_FORMAT(partnership_request.CreatedAt, '%d-%m-%Y') AS RequestDate\n" + 
			"			FROM \n" + 
			"			    cdt_website.partnership_request partnership_request\n" + 
			"			    inner join cdt_master_data.agm_industries agm_industries on(partnership_request.IndustryID = agm_industries.ID)\n" + 
			"			    inner join cdt_master_data.agm_partner_programs agm_partner_programs on(partnership_request.PartnershipProgramID = agm_partner_programs.ID)\n" + 
			"			    where partnership_request.Name like :searchText\n" + 
			"			    or partnership_request.Email like :searchText\n" + 
			"			    or partnership_request.Mobile like :searchText\n" + 
			"			    or partnership_request.OrganisationName like :searchText", countQuery = "SELECT partnership_request.ID,\n" + 
					"			    UPPER(partnership_request.Name) as Name,\n" + 
					"			    partnership_request.Email,\n" + 
					"			    partnership_request.Mobile,\n" + 
					"			    UPPER(partnership_request.OrganisationName) as OrganisationName,\n" + 
					"                DATE_FORMAT(partnership_request.CreatedAt, '%d-%m-%Y') AS RequestDate\n" + 
					"			FROM \n" + 
					"			    cdt_website.partnership_request partnership_request\n" + 
					"			    inner join cdt_master_data.agm_industries agm_industries on(partnership_request.IndustryID = agm_industries.ID)\n" + 
					"			    inner join cdt_master_data.agm_partner_programs agm_partner_programs on(partnership_request.PartnershipProgramID = agm_partner_programs.ID)\n" + 
					"			    where partnership_request.Name like :searchText\n" + 
					"			    or partnership_request.Email like :searchText\n" + 
					"			    or partnership_request.Mobile like :searchText\n" + 
					"			    or partnership_request.OrganisationName like :searchText", nativeQuery = true)
	Page<PartnershipRequestVO> getAllPartnershipRequestByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT \n" + 
			"    partnership_request.ID,\n" + 
			"    partnership_request.Name,\n" + 
			"    partnership_request.Email,\n" + 
			"    partnership_request.Mobile,\n" + 
			"    partnership_request.OrganisationName,\n" + 
			"    partnership_request.OrganisationEmail,\n" + 
			"    agm_industries.Name AS Industry,\n" + 
			"    partnership_request.OtherIndustry,\n" + 
			"    agm_partner_programs.Name AS PartnershipPrograms,\n" + 
			"    partnership_request.More,\n" + 
			"	DATE_FORMAT(partnership_request.CreatedAt, '%d-%m-%Y') AS RequestDate\n" + 
			"FROM\n" + 
			"    cdt_website.partnership_request partnership_request\n" + 
			"        INNER JOIN\n" + 
			"    cdt_master_data.agm_industries agm_industries ON (partnership_request.IndustryID = agm_industries.ID)\n" + 
			"        INNER JOIN\n" + 
			"    cdt_master_data.agm_partner_programs agm_partner_programs ON (partnership_request.PartnershipProgramID = agm_partner_programs.ID)\n" + 
			"WHERE\n" + 
			"    partnership_request.ID = ?1", nativeQuery = true)
	PartnershipRequestVO getPartnershipRequestById(Integer id);

}
	