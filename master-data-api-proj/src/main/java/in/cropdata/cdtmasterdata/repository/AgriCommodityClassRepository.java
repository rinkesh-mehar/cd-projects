package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriCommodityClass;

public interface AgriCommodityClassRepository extends JpaRepository<AgriCommodityClass, Integer> {
	
	@Query(value="select ID,Name,Status from agri_commodity_class\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from agri_commodity_class\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<AgriCommodityClass> getCommodityClassListByPagenation(Pageable sortedByIdDesc, String searchText);


}
