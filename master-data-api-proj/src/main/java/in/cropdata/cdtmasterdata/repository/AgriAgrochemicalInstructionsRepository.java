package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.model.AgriAgrochemicalInstructions;
import in.cropdata.cdtmasterdata.model.vo.AgriAgrochemicalInstructionsVo;
public interface AgriAgrochemicalInstructionsRepository extends JpaRepository<AgriAgrochemicalInstructions, Integer> {
	
	@Query(value = "select ID,Name,Status from agri_agrochemical_instructions\n" + 
			"where ID like :searchText\n" + 
			"OR Name like :searchText\n" + 
			"OR Status like :searchText\n" + 
			"OR Name like :searchText",countQuery = "select ID,Name,Status from agri_agrochemical_instructions\n" + 
					"where ID like :searchText\n" + 
					"OR Name like :searchText\n" + 
					"OR Status like :searchText",nativeQuery = true)
	Page<AgriAgrochemicalInstructionsVo> getAgrochemicalInstructionsPaginatedList(Pageable sortedByIdDesc,String searchText);
	
	@Modifying
    @Transactional
	@Query(value="update agri_agrochemical_instructions set status = 'Approved'\n" + 
			"where id = ?1",nativeQuery = true)
	int approveCommodityGroup(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update agri_agrochemical_instructions set status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int finalizeCommodityGroup(Integer id);

	
	@Modifying
    @Transactional
	@Query(value="update agri_agrochemical_instructions set status = 'Rejected'\n" + 
			"where id = ?1",nativeQuery = true)
	int rejectCommodityGroup(Integer id);


}
