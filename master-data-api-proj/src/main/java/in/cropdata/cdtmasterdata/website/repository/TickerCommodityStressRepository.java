package in.cropdata.cdtmasterdata.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.model.TickerCommodityStress;

@Repository
@Transactional
public interface TickerCommodityStressRepository extends JpaRepository<TickerCommodityStress, Integer> {

	@Modifying
	@Query(value = "delete from  ticker_commodity_stress where TickerID=?1", nativeQuery = true)
	void deleteByTickerId(Integer id);

	@Modifying
    @Transactional
    @Query(value = "update cdt_website.ticker_commodity_stress TCS SET TCS.status = 'Deleted' WHERE TCS.TickerID = ?1", nativeQuery = true)
    int deleteCommodityStressById(Integer commodityStressId);
}
