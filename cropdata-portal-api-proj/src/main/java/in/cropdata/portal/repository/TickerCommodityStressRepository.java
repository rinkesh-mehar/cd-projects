package in.cropdata.portal.repository;

import in.cropdata.portal.model.TickerCommodityStress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TickerCommodityStressRepository extends JpaRepository<TickerCommodityStress, Integer> {

}
