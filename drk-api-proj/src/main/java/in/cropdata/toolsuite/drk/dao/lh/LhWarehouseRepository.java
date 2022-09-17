package in.cropdata.toolsuite.drk.dao.lh;

import in.cropdata.toolsuite.drk.model.lh.LhWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.toolsuite.drk.dao.lh
 * @date 18/02/21
 * @time 1:05 PM
 */
@Repository
public interface LhWarehouseRepository extends JpaRepository<LhWarehouse, Integer>
{

}
