package in.cropdata.portal.repository;

import in.cropdata.portal.model.ApplicantApplicationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.model.ApplicantContactDetails;

import java.util.Optional;


@Repository
public interface ApplicantContactDetailsRepository extends JpaRepository<ApplicantContactDetails, Integer> {



}
