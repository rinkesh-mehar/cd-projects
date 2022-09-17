package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.acl.model.AclGroup;

public interface AclGroupRepository extends JpaRepository<AclGroup, Integer> {

}
