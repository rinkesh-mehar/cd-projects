package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.acl.model.AclRole;

public interface AclRoleRepository extends JpaRepository<AclRole, Integer> {

}
