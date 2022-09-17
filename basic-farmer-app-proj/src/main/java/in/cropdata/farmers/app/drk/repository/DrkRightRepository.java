package in.cropdata.farmers.app.drk.repository;

import in.cropdata.farmers.app.drk.model.DrkRights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrkRightRepository extends JpaRepository<DrkRights, String> {

    List<DrkRights> findDrkRightsByCaseIdIn(List<String> caseList);
}
