package in.cropdata.farmers.app.drk.repository;

import in.cropdata.farmers.app.drk.model.ImageStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author RinkeshKM
 * @Date 18/02/21
 */

@Repository
public interface ImageStoreRepository extends JpaRepository<ImageStore, Integer> {

    Optional<ImageStore> findByMetaIDAndFarmerID(Integer metaID, String farmerID);
}
