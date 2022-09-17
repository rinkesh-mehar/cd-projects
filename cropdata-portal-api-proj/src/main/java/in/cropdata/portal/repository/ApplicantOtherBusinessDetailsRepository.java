package in.cropdata.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.model.ApplicantOtherBusinessDetails;

import java.util.Optional;

@Repository
public interface ApplicantOtherBusinessDetailsRepository extends JpaRepository<ApplicantOtherBusinessDetails, Integer> {

    Optional<ApplicantOtherBusinessDetails> findByApplicantID(Integer id);


}
