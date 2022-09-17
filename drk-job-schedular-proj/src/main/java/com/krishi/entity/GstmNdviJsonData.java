package com.krishi.entity;

import com.krishi.model.GstmNdviResponseModel;
import com.krishi.model.GstmSimpleNdviResponseModel;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package com.krishi.entity
 * @date 17/12/21
 * @time 1:20 PM
 */
public class GstmNdviJsonData
{
    private String success;
    private String errorCode;
    private String errorMsg;
    List<GstmNdviResponseModel> data;

    public String getSuccess()
    {
        return success;
    }

    public void setSuccess(String success)
    {
        this.success = success;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public List<GstmNdviResponseModel> getData()
    {
        return data;
    }

    public void setData(List<GstmNdviResponseModel> data)
    {
        this.data = data;
    }
}
