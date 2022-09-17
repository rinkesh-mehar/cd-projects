package in.cropdata.gstm.studio.dao;

import java.util.List;

import in.cropdata.gstm.studio.model.DropDownFilters;

/**
 * Repository interface to create useful methods for DB operations.
 * 
 * @author PranaySK
 * @since 1.0
 */

public interface GstmStudioRepository {

	List<DropDownFilters> getAllStates();

	List<DropDownFilters> getDistricts(Integer stateCode);

	List<DropDownFilters> getAllPlatforms();

	List<DropDownFilters> getAllDataTypes(Integer platformId);

	List<DropDownFilters> getDataTypeCategory(Integer dataTypeId);

	List<DropDownFilters> getSubCategory(Integer categoryId);

	List<DropDownFilters> getSubCategoryParameters(Integer subCategoryId);

}
