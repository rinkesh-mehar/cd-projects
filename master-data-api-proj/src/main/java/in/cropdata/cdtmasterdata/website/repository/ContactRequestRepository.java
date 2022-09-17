/**
 * 
 */
package in.cropdata.cdtmasterdata.website.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.website.model.ContactRequest;
import in.cropdata.cdtmasterdata.website.model.vo.ContactRequestVO;

/**
 * @author cropdata-kunal
 *
 */
public interface ContactRequestRepository extends JpaRepository<ContactRequest, Integer> {
	
	@Query(value = "SELECT \n" + 
			"		ID, UPPER(Name) as Name, Email, Mobile, UPPER(Query) as Query\n" + 
			"FROM\n" + 
			"    cdt_website.contact_request\n" + 
			"WHERE\n" + 
			"    ID LIKE :searchText\n" + 
			"    OR Name LIKE :searchText\n" + 
			"	OR Email LIKE :searchText\n" + 
			"	OR Mobile LIKE :searchText\n" + 
			"	OR Query LIKE :searchText", countQuery = "SELECT \n" + 
					"		ID, UPPER(Name) as Name, Email, Mobile, UPPER(Query) as Query\n" + 
					"FROM\n" + 
					"    cdt_website.contact_request\n" + 
					"WHERE\n" + 
					"    ID LIKE :searchText\n" + 
					"    OR Name LIKE :searchText\n" + 
					"	OR Email LIKE :searchText\n" + 
					"	OR Mobile LIKE :searchText\n" + 
					"	OR Query LIKE :searchText", nativeQuery = true)
	Page<ContactRequestVO> getAllContactRequestByPagenation(Pageable sortedByIdDesc, String searchText);

}
