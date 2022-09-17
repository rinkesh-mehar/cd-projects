package in.cropdata.cdtmasterdata.gstmDataModels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.gstmDataModels.dto
 * @date 30/07/20
 * @time 2:42 PM
 * To change this template use File | Settings | File and Code Templates
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CdtModuleDto
{
    public Integer getId();

    public String getModelName();

    public String getFileUrl();

    public String getErrMsg();

    public String getStatus();
}
