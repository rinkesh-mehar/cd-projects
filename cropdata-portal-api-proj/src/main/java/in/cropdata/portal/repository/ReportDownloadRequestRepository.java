package in.cropdata.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.model.ReportDownloadRequest;

@Repository
public interface ReportDownloadRequestRepository extends JpaRepository<ReportDownloadRequest, Integer> {

}
