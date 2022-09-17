package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.acl.model.AclResource;

public interface AclResourceRepository extends JpaRepository<AclResource, Integer> {

    @Query(value = "SELECT * FROM cdt_master_data.acl_resource where ParentID=0 order by ResourceName",nativeQuery = true)
    List<AclResource> getAllParentResource();
    
    @Query(value = "SELECT * FROM cdt_master_data.acl_resource where ParentID=?1",nativeQuery = true)
    List<AclResource> getAllSubResourceByParentId(int parentId);
}
