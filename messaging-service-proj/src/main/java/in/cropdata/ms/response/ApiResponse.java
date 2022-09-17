package in.cropdata.ms.response;

import in.cropdata.ms.error.ErrorStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Vivek Gajbhiye
 */
@Component
public class ApiResponse {

    public Api response(final boolean success,final String message,final String errorCode){
        Api api = new Api();
        api.setSuccess(success);
        if(api.isSuccess()){
            api.setSuccess(true);
            api.setMessage(message);
            api.setErrorCode("000");
        }else{
            api.setSuccess(false);
            api.setMessage(message);
            api.setErrorCode(errorCode);
        }
        return api;
    }

    public Api response(final boolean success, ErrorStatus errorStatus){
        Api api = new Api();
        api.setSuccess(success);
        if(api.isSuccess()){
            api.setSuccess(true);
            api.setMessage(errorStatus.getReasonPhrase());
            api.setErrorCode(errorStatus.getErrorCode());
        }else{
            api.setSuccess(false);
            api.setMessage(errorStatus.getReasonPhrase());
            api.setErrorCode(errorStatus.getErrorCode());
        }
        return api;
    }
    public Api response(final boolean success, final Map<String,String> data, final ErrorStatus errorStatus){
        Api api = new Api();
        api.setSuccess(success);
        if(api.isSuccess()){
            api.setSuccess(true);
            api.setMessage(errorStatus.getReasonPhrase());
            api.setErrorCode(errorStatus.getErrorCode());
            api.setData(data);
        }else{
            api.setSuccess(false);
            api.setMessage(errorStatus.getReasonPhrase());
            api.setErrorCode(errorStatus.getErrorCode());
        }
        return api;
    }
}
