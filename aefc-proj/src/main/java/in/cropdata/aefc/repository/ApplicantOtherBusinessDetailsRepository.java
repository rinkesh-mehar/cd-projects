package in.cropdata.aefc.repository;

import in.cropdata.aefc.entity.ApplicantOtherBusinessDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantOtherBusinessDetailsRepository extends JpaRepository<ApplicantOtherBusinessDetails, Integer> {

}