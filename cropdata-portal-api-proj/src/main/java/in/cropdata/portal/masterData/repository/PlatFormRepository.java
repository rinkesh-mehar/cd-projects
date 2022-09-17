package in.cropdata.portal.masterData.repository;

import in.cropdata.portal.masterData.model.PlatFormMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.repository
 * @date 25/08/20
 * @time 10:01 AM
 * To change this template use File | Settings | File and Code Templates
 */
@Repository
public interface PlatFormRepository extends JpaRepository<PlatFormMaster, Integer>
{

}
