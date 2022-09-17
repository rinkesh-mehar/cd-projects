package in.cropdata.cdtmasterdata.gstmDataModels.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.model.vo
 * @date 30/07/20
 * @time 9:42 AM
 * To change this template use File | Settings | File and Code Templates
 */
@Data
public class CdtManageVo
{
    private MultipartFile csvFile;

    private String data;


}
