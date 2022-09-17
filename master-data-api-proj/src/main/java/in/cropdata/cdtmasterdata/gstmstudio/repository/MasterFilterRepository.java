package in.cropdata.cdtmasterdata.gstmstudio.repository;

import in.cropdata.cdtmasterdata.gstmstudio.model.FilterMaster;
import in.cropdata.cdtmasterdata.gstmstudio.dto.GstmEyeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author RinkeshKM
 * @Date 28/07/20
 */

@Repository
public interface MasterFilterRepository extends JpaRepository<FilterMaster, Integer> {

    @Query(value = "select FM.ID, fPlat.Name AS Platform, FD.Name AS DataType, FC.Name AS Category, FSC.Name AS SubCategory, FP.Name AS Parameter \n" +
            "from gstm_studio.filter_master AS FM \n" +
            "INNER JOIN gstm_studio.filter_platform AS fPlat ON (FM.PlatformID = fPlat.ID) \n" +
            "INNER JOIN gstm_studio.filter_parameters AS FP ON (FM.ParameterID = FP.ID) \n" +
            "INNER JOIN gstm_studio.filter_datatype AS FD ON (FM.DataTypeID = FD.ID) \n" +
            "INNER JOIN gstm_studio.filter_category AS FC ON (FM.CategoryID = FC.Id) \n" +
            "INNER JOIN gstm_studio.filter_category AS FSC ON (FSC.ID = FM.SubCategoryID) ORDER BY FM.ID ", nativeQuery = true)
    List<GstmEyeDto> getParametersList();


    @Query(value = "select FM.ID, FM.Status AS Status, fPlat.Name AS Platform, FD.Name AS DataType, FC.Name AS Category, FSC.Name AS SubCategory, FP.Name AS Parameter \n" +
            "from gstm_studio.filter_master AS FM \n" +
            "INNER JOIN gstm_studio.filter_platform AS fPlat ON (FM.PlatformID = fPlat.ID) \n" +
            "INNER JOIN gstm_studio.filter_parameters AS FP ON (FM.ParameterID = FP.ID) \n" +
            "INNER JOIN gstm_studio.filter_datatype AS FD ON (FM.DataTypeID = FD.ID) \n" +
            "INNER JOIN gstm_studio.filter_category AS FC ON (FM.CategoryID = FC.Id) \n" +
            "INNER JOIN gstm_studio.filter_category AS FSC ON (FSC.ID = FM.SubCategoryID)\n" +
            "WHERE fPlat.Name LIKE :searchText OR FD.Name LIKE :searchText OR FC.Name LIKE :searchText OR FSC.Name LIKE :searchText OR FP.Name LIKE :searchText ", countQuery = "select \n" +
            "FM.ID, FM.Status AS Status, fPlat.Name AS Platform, FD.Name AS DataType, FC.Name AS Category, FSC.Name AS SubCategory, FP.Name AS Parameter \n" +
            "from gstm_studio.filter_master AS FM \n" +
            "INNER JOIN gstm_studio.filter_platform AS fPlat ON (FM.PlatformID = fPlat.ID) \n" +
            "INNER JOIN gstm_studio.filter_parameters AS FP ON (FM.ParameterID = FP.ID) \n" +
            "INNER JOIN gstm_studio.filter_datatype AS FD ON (FM.DataTypeID = FD.ID) \n" +
            "INNER JOIN gstm_studio.filter_category AS FC ON (FM.CategoryID = FC.Id) \n" +
            "INNER JOIN gstm_studio.filter_category AS FSC ON (FSC.ID = FM.SubCategoryID)\n" +
            "WHERE fPlat.Name LIKE :searchText OR FD.Name LIKE :searchText OR FC.Name LIKE :searchText OR FSC.Name LIKE :searchText OR FP.Name LIKE :searchText", nativeQuery = true)
    Page<GstmEyeDto> getAllParametersListPaginated(Pageable sortedByIdAsc, String searchText);

    @Query(value = "select FM.ID, FM.Status Status, FP.Name parameterName, FPLAT.ID platformId, FD.ID dataTypeId, FC.ID categoryId, FSC.ID subCategoryId from gstm_studio.filter_master FM\n" +
            "INNER JOIN gstm_studio.filter_parameters FP ON (FM.ParameterID = FP.ID)\n" +
            "INNER JOIN gstm_studio.filter_platform FPLAT ON (FM.PlatformID = FPLAT.ID)\n" +
            "INNER JOIN gstm_studio.filter_datatype FD ON (FM.DataTypeID = FD.ID)\n" +
            "INNER JOIN gstm_studio.filter_category FC ON (FM.CategoryID = FC.ID)\n" +
            "INNER JOIN gstm_studio.filter_category FSC ON (FM.SubCategoryID = FSC.ID)\n" +
            "WHERE FM.ID = ?1", nativeQuery = true)
    GstmEyeDto getParameterByParameterId(int parameterId);


    @Query(value = "select FP.Id, FP.FullName AS name from gstm_studio.filter_platform FP order by FP.FullName", nativeQuery = true)
    List<GstmEyeDto> getAllPlatform();

    @Query(value = "SELECT distinct(fd.ID) as ID, fd.Name FROM gstm_studio.filter_master as fm\n" +
            "INNER JOIN gstm_studio.filter_datatype as fd on fd.ID = fm.DataTypeID\n" +
            "WHERE 1 and fm.PlatformID = :platformId order by fd.Name", nativeQuery = true)
    List<GstmEyeDto> getDataTypeByPlatformId(int platformId);

    @Query(value = "SELECT distinct(fc.ID) as ID, fc.Name FROM gstm_studio.filter_master as fm\n" +
            "INNER JOIN gstm_studio.filter_category as fc on fc.ID = fm.CategoryID\n" +
            "WHERE 1 and fm.DataTypeID = :dataTypeId order by fc.Name", nativeQuery = true)
    List<GstmEyeDto> getCategoryByDataType(final Integer dataTypeId);


    @Query(value = "SELECT distinct(fc.ID) as ID, fc.Name FROM gstm_studio.filter_master as fm\n" +
            "INNER JOIN gstm_studio.filter_category as fc on fc.ID = fm.SubCategoryID\n" +
            "WHERE 1 and fm.CategoryID = :categoryId order by fc.Name", nativeQuery = true)
    List<GstmEyeDto> getSubcategoryByCategory(final Integer categoryId);

    @Transactional
    @Modifying
    @Query(value = "insert into gstm_studio.filter_master (platformid, datatypeid, categoryid, subcategoryid, parameterid)\n" +
            "value (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    int saveParameterInMaster(int platformId, int dataTypeId, int categoryId, int subcategoryId, long parameterId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE gstm_studio.filter_master FM SET FM.Status = 'Deleted' WHERE FM.ID = ?1", nativeQuery = true)
    int changeStatusToDeleteById(int parameterId);

    @Query(value = "select fm.ParameterID from gstm_studio.filter_master fm where fm.ID = ?1", nativeQuery = true)
    int getParamIdByMasterId(int parameterId);
}
