package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.dto.interfaces.GeneralTerrainTypeDto;
import in.cropdata.cdtmasterdata.model.GeneralTerrainType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * @author RinkeshKM
 * @Date 09/11/20
 */

@Repository
public interface GeneralTerrainRepository extends JpaRepository<GeneralTerrainType, Integer> {

    @Query(value = "select ID, gtt.RegionID, GR.Name as RegionName, TerrainType, MinPerKm, gtt.Status, gtt.IsValid, gtt.ErrorMessage from cdt_master_data.general_terrain_type gtt\n" +
            "        left join geo_region GR on (gtt.RegionID = GR.RegionID)\n" +
            "    where GR.Name like :searchText\n" +
            "or gtt.TerrainType like :searchText\n" +
            "or gtt.MinPerKm like :searchText\n" +
            "or gtt.Status like :searchText",
            countQuery = "select ID, gtt.RegionID, GR.Name, TerrainType, MinPerKm, gtt.Status, gtt.IsValid, gtt.ErrorMessage from cdt_master_data.general_terrain_type gtt\n" +
                    "        left join geo_region GR on (gtt.RegionID = GR.RegionID)\n" +
                    "    where GR.Name like :searchText\n" +
                    "or gtt.TerrainType like :searchText\n" +
                    "or gtt.MinPerKm like :searchText\n" +
                    "or gtt.Status like :searchText",
            nativeQuery = true)
    Page<GeneralTerrainTypeDto> getGeneralTerrainList(Pageable pageable, String searchText);

    @Query(value = "select ID, gtt.RegionID, GR.Name as RegionName, TerrainType, MinPerKm, gtt.Status, gtt.IsValid, gtt.ErrorMessage from cdt_master_data.general_terrain_type gtt\n" +
            "        left join geo_region GR on (gtt.RegionID = GR.RegionID)\n" +
            "    where gtt.IsValid = 0 and (GR.Name like :searchText\n" +
            "or gtt.TerrainType like :searchText\n" +
            "or gtt.MinPerKm like :searchText\n" +
            "or gtt.Status like :searchText)",
            countQuery = "select ID, gtt.RegionID, GR.Name, TerrainType, MinPerKm, gtt.Status, gtt.IsValid, gtt.ErrorMessage from cdt_master_data.general_terrain_type gtt\n" +
                    "        left join geo_region GR on (gtt.RegionID = GR.RegionID)\n" +
                    "    where gtt.IsValid = 0 and (GR.Name like :searchText\n" +
                    "or gtt.TerrainType like :searchText\n" +
                    "or gtt.MinPerKm like :searchText\n" +
                    "or gtt.Status like :searchText)",
            nativeQuery = true)
    Page<GeneralTerrainTypeDto> getGeneralTerrainListInvalidated(Pageable pageable, String searchText);

    Optional<GeneralTerrainType> findByTerrainTypeAndRegionId(String terrainType, Integer regionId);
}
