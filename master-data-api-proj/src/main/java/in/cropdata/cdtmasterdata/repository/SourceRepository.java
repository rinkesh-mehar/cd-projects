package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.region.model.SourceModel;
import in.cropdata.cdtmasterdata.region.vo.SourceVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.repository
 * @date 18/01/21
 * @time 12:56 PM
 */
@Repository
public interface SourceRepository extends JpaRepository<SourceModel, Integer>
{
    @Query(value = "select ds.ID,ds.Name,ds.Address,ds.Description,ds.Headquarter,\n" +
            "       gc.Name countryName,gs.Name stateName\n" +
            "from cdt_master_data.drkrishi_source ds\n" +
            "left join cdt_master_data.geo_country gc on (gc.CountryCode = ds.CountryCode)\n" +
            "left join cdt_master_data.geo_state gs on (gs.StateCode=ds.StateCode)\n" +
            "where (gc.Name like :searchText or gs.Name like :searchText\n" +
            "   or ds.Address like :searchText or ds.Headquarter like :searchText)",
            countQuery = "select ds.ID,ds.Name,ds.Address,ds.Description,ds.Headquarter,\n" +
                    "       gc.Name countryName,gs.Name stateName\n" +
                    "from cdt_master_data.drkrishi_source ds\n" +
                    "left join cdt_master_data.geo_country gc on gc.CountryCode = ds.CountryCode\n" +
                    "left join cdt_master_data.geo_state gs on gs.StateCode=ds.StateCode\n" +
                    "where (gc.Name like :searchText or gs.Name like :searchText\n" +
                    "   or ds.Address like :searchText or ds.Headquarter like :searchText)",nativeQuery = true)
    Page<SourceVO> getSourceList(Pageable size, String searchText);
}
