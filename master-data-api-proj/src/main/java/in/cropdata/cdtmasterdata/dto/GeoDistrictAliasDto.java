package in.cropdata.cdtmasterdata.dto;

import in.cropdata.cdtmasterdata.dto.interfaces.AliasDto;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoDistrictInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoTehsilInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoVillageInfDto;
import lombok.Data;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.dto
 * @date 28/07/20
 * @time 6:57 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Data
@JsonInclude(value = Include.NON_NULL)
public class GeoDistrictAliasDto
{
	private Page<AliasDto> aliasDtoPage;

	private List<GeoDistrictInfDto> districtInfoDtoList;

	private List<GeoTehsilInfDto> tehsilInfoDtoList;

	private List<GeoVillageInfDto> villageInfoDtoList;
}
