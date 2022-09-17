package in.cropdata.cdtmasterdata.gstmstudio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author RinkeshKM
 * @Date 27/07/20
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface GstmEyeDto {
    int getId();
    String getName();
    String getPlatform();
    String getDataType();
    String getCategory();
    String getSubCategory();
    String getParameter();
    String getParameterName();

    String getPlatformId();
    String getDataTypeId();
    String getCategoryId();
    String getSubCategoryId();
    String getStatus();


}
