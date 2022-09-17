package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.model.ProductsAndServices;
import in.cropdata.cdtmasterdata.website.model.vo.ProductsAndServicesVO;

@Repository
public interface ProductsAndServicesRepository extends JpaRepository<ProductsAndServices, Integer> {

	@Query(value="SELECT\n" + 
			"						    services.ID,\n" + 
			"						    UPPER(services.Name) AS Name,\n" + 
			"				            UPPER(services.Description) as Description,\n" + 
			"						    UPPER(pm.Name) As Platform,\n" + 
			"		                    services.Logo,\n" + 
			"						    services.Status \n" + 
			"						FROM\n" + 
			"						    services services\n" + 
			"						        left JOIN \n" + 
			"						    cdt_master_data.platform_master pm ON (pm.ID = services.PlatformID) \n" + 
			"						WHERE\n" + 
			"						    services.Status <> 'Deleted'\n" + 
			"						    AND (services.ID like :searchText\n" + 
			"						        OR services.Name LIKE :searchText\n" + 
			"								OR services.Description like :searchText\n" + 
			"						        OR pm.Name LIKE :searchText\n" + 
			"						        OR services.Status LIKE :searchText)",countQuery = "SELECT\n" + 
					"						    services.ID,\n" + 
					"						    UPPER(services.Name) AS Name,\n" + 
					"				            UPPER(services.Description) as Description,\n" + 
					"						    UPPER(pm.Name) As Platform,\n" + 
					"		                    services.Logo,\n" + 
					"						    services.Status \n" + 
					"						FROM\n" + 
					"						    services services\n" + 
					"						        left JOIN \n" + 
					"						    cdt_master_data.platform_master pm ON (pm.ID = services.PlatformID) \n" + 
					"						WHERE\n" + 
					"						    services.Status <> 'Deleted'\n" + 
					"						    AND (services.ID like :searchText\n" + 
					"						        OR services.Name LIKE :searchText\n" + 
					"								OR services.Description like :searchText\n" + 
					"						        OR pm.Name LIKE :searchText\n" + 
					"						        OR services.Status LIKE :searchText)",nativeQuery = true)
	Page<ProductsAndServicesVO> getProductsAndServicesListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="select count(*) as count from services where Status <> 'Deleted' and Name= ?1 and PlatformID = ?2",nativeQuery = true)
	Integer findAlreadyExistProductsAndServicesForAddMode(String name,Integer platformId);
	
	@Query(value="select ID,Name from services where Status in('Active')",nativeQuery = true)
	List<ProductsAndServicesVO> getProductsAndServicesList();
	
	@Query(value="select count(*) as count from services where Status <> 'Deleted' and ID <> ?1 and Name= ?2 and PlatformID = ?3",nativeQuery = true)
	Integer findAlreadyExistProductsAndServicesForEditMode(Integer id, String name,Integer platformId);
	
	@Modifying
    @Transactional
	@Query(value="update services set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activeProductsAndServices(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update services set status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactiveProductsAndServices(Integer id);
	
	
	@Modifying
    @Transactional
	@Query(value="update services set status = 'Deleted'\n" + 
			"where id = ?1",nativeQuery = true)
	int deleteProductsAndServices(Integer id);
	
}
