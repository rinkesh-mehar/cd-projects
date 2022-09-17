package in.cropdata.cdtmasterdata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.cropdata.cdtmasterdata.dto.interfaces.AliasDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityInfo;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.dto
 * @date 28/07/20
 * @time 11:52 AM
 * To change this template use File | Settings | File and Code Templates
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AgriCommodityAlias
{

    private Page<AliasDto> agriCommodityAliasDtoList;

    private List<AgriCommodityInfo> agriCommodityInfos;
}
