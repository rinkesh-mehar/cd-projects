package in.cropdata.cdtmasterdata.gstmstudio.repository;

import in.cropdata.cdtmasterdata.gstmstudio.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author RinkeshKM
 * @Date 28/07/20
 */

@Repository
public interface ParameterFilterRepository extends JpaRepository<Parameter, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE gstm_studio.filter_parameters FP SET FP.Status = 'Deleted' WHERE FP.ID = ?1", nativeQuery = true)
    int changeStatusToDeleteById(Integer parameterId);

    @Modifying
    @Transactional
    @Query(value = "update gstm_studio.filter_parameters fp set fp.Name = ?1, fp.Status = ?3 where fp.ID = ?2", nativeQuery = true)
    int updateParameter(String parameterName, Integer id, String status);
}
