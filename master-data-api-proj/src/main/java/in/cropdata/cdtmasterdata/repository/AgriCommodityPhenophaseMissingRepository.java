package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriCommodityPhenophaseMissing;

@Repository
public interface AgriCommodityPhenophaseMissingRepository extends JpaRepository<AgriCommodityPhenophaseMissing, Integer> {

}
