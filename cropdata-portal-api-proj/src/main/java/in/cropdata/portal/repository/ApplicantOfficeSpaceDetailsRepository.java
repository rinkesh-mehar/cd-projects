package in.cropdata.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import in.cropdata.portal.model.ApplicantOfficeSpaceDetails;

@Repository
public interface ApplicantOfficeSpaceDetailsRepository extends JpaRepository<ApplicantOfficeSpaceDetails, Integer> {

}
