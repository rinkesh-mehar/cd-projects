package in.cropdata.toolsuite.drk.repository.tileassignment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.toolsuite.drk.dto.tileassignment.VillageDTOInf;
import in.cropdata.toolsuite.drk.model.tileassignment.GeoVillage;

public interface GeoVillageRepository extends JpaRepository<GeoVillage, Integer> {

    @Query(value = "SELECT gv.VillageCode as villageCode,if(gv.PanchayatCode > 0,concat(gv.Name,' - ',gp.Name,' - ',gt.Name), concat(gv.Name)) as villageName \n" +
    	"FROM geo_village gv\n" + 
    	"INNER JOIN geo_tehsil gt ON gv.TehsilCode = gt.TehsilCode\n" + 
    	"left JOIN geo_panchayat gp ON gp.PanchayatCode=gv.PanchayatCode\n" +
    	"WHERE gv.SubRegionID =?1 order by gv.Name",nativeQuery = true)
    List<VillageDTOInf> findVillageCodeAndName(String subRegionID);
    
    
}
