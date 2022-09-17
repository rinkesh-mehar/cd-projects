package in.cropdata.cdtmasterdata.gstmDataModels.repository;

import in.cropdata.cdtmasterdata.gstmDataModels.dto.CdtModuleDto;
import in.cropdata.cdtmasterdata.gstmDataModels.model.CdtManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.repository
 * @date 29/07/20
 * @time 7:33 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Repository
public interface CdtManageRepository extends JpaRepository<CdtManage, Integer>
{
   /* Optional<CdtModule> findById(int id);*/

    @Query(value = "select rdf.ID id ,ModelName,FileUrl, Errmsg ,rdf.Status from gstm_data_models.raw_data_files rdf\n" +
            "inner join gstm_data_models.raw_model rm on rm.ID = rdf.ModelID where rdf.Status in('Failed', 'Pending','Processed')", nativeQuery = true)
    List<CdtModuleDto> getManageList();

    @Query(value = "select rdf.ID id ,rm.ModelName,rdf.FileUrl, rdf.Errmsg ,rdf.Status from gstm_data_models.raw_data_files rdf\n" +
            "inner join gstm_data_models.raw_model rm on rm.ID = rdf.ModelID\n" +
            "where rdf.Status in('Failed', 'Pending','Processed')\n" +
            "and rm.ModelName like :searchText or rdf.Status like :searchText\n",
            countQuery = "select rdf.ID id ,rm.ModelName,rdf.FileUrl, rdf.Errmsg ,rdf.Status from gstm_data_models.raw_data_files rdf\n" +
            "inner join gstm_data_models.raw_model rm on rm.ID = rdf.ModelID\n" +
            "where rdf.Status in('Failed', 'Pending','Processed')\n" +
            "and rm.ModelName like :searchText or rdf.Status like :searchText", nativeQuery = true)
    Page<CdtModuleDto> getPageableList(Pageable sortByIdAsc, String searchText);

    @Query(value = "select ID, ModelName from gstm_data_models.raw_model order by ModelName", nativeQuery = true)
    List<Map<String, String>> listOfModels();


    @Transactional
    @Modifying
    @Query(value = "insert into gstm_data_models.raw_data_files (ModelID, FileUrl) values (?1, ?2) ", nativeQuery = true)
    int addModels(Integer ManageId, String csvFileUrl);

    @Transactional
    @Modifying
    @Query(value = " update gstm_data_models.raw_data_files set Status = ?1 where ID= ?2", nativeQuery = true)
    int deleteRecords(String status, int Id);

    @Transactional
    @Modifying
    @Query(value = "update gstm_data_models.raw_data_files set Status = ?3, FileUrl = ?2 ,ErrMsg= ?4 where ID= ?1", nativeQuery = true)
    int updateRecord(Integer Id, String csvFileUrl, String status, String ErrMsg);
}
