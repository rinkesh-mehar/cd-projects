package in.cropdata.cdtmasterdata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityInfo;
import in.cropdata.cdtmasterdata.model.GeneralUom;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralUomRepository extends JpaRepository<GeneralUom, Integer> {
	List<GeneralUom> findAllByOrderByNameAsc();
	
	@Query(value = "SELECT ID,Name,Description,Status FROM cdt_master_data.general_uom\n" + 
			"where ID like :searchText\n" + 
			"OR Name  like :searchText\n" + 
			"OR Description  like :searchText\n" + 
			"OR Status  like :searchText", countQuery = "SELECT ID,Name,Description,Status FROM cdt_master_data.general_uom\n" + 
					"where ID like :searchText\n" + 
					"OR Name  like :searchText\n" + 
					"OR Description  like :searchText\n" + 
					"OR Status  like :searchText", nativeQuery = true)
	Page<GeneralUom> getPeginatedGeneralUomList(Pageable sortedByIdDesc, String searchText);

	Optional<GeneralUom> findByName(String name);
}
