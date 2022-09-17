package in.cropdata.aefc.repository;

import in.cropdata.aefc.entity.ApplicantApplicationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantApplicationDetailRepository extends JpaRepository<ApplicantApplicationDetail, Integer> {

}