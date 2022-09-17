package in.cropdata.cdtmasterdata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.model.AgriSeason;
import in.cropdata.cdtmasterdata.model.vo.AgriSeasonVO;

public interface AgriSeasonRepository extends JpaRepository<AgriSeason, Integer> {
	
	Optional<AgriSeason> findByName(String name);
	
	@Query(value = "SELECT * FROM agri_season WHERE ID IN(SELECT SeasonID FROM regional_season where StateCode = ?1) order by Name", nativeQuery = true)
	List<AgriSeason> getSeasonByStateCode( int stateCode);
	
	@Query(value = "select distinct agri_season.ID,agri_season.Name from agri_season  agri_season\n" + 
			"Inner Join agri_season_commodity agri_season_commodity ON (agri_season.ID = agri_season_commodity.SeasonID) order by agri_season.Name", nativeQuery = true)
	List<AgriSeasonVO> getSeasonList();
	

	@Query(value="select ID,Name,Status from agri_season\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,Name,Status from agri_season\n" + 
					"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<AgriSeason> getSeasonListByPagenation(Pageable sortedByIdDesc, String searchText);
}
