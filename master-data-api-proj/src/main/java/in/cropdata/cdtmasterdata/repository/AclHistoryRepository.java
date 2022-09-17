package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.acl.model.AclHistory;

public interface AclHistoryRepository extends JpaRepository<AclHistory, Integer> {

}
