package in.cropdata.cdtmasterdata.gstmDataModels.repository;

import in.cropdata.cdtmasterdata.gstmDataModels.model.Model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.gstmDataModels.repository
 * @date 05/09/20
 * @time 12:57 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Repository
public interface ModelRepository extends JpaRepository<Model, Integer>
{
    public String getModelTemplatesById(Integer id);
    
    @Query(value="select ID,ModelName,ModelDescription,ModelTemplates,Status from raw_model\n" + 
    		"    where ID  like :searchText OR ModelName like :searchText OR ModelDescription like :searchText OR ModelTemplates like :searchText OR  Status like :searchText",countQuery = "select ID,ModelName,ModelDescription,ModelTemplates,Status from raw_model\n" + 
    				"    where ID  like :searchText OR ModelName like :searchText OR ModelDescription like :searchText OR ModelTemplates like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<Model> getModelListByPagenation(Pageable sortedByIdDesc, String searchText);

    

}
