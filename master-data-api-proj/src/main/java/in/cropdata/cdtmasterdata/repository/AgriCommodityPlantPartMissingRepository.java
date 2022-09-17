package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.model.AgriCommodityPlantPartMissing;
@Repository
public interface AgriCommodityPlantPartMissingRepository extends JpaRepository<AgriCommodityPlantPartMissing, Integer> {

}
