package in.cropdata.cdtmasterdata.dto;

import in.cropdata.cdtmasterdata.dto.interfaces.AliasDto;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoStateInfDto;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.dto
 * @date 28/07/20
 * @time 5:59 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Data
public class GeoStateAliasDto
{
    private Page<AliasDto> aliasList;

    private List<GeoStateInfDto> stateList;
}
