/**
 *
 */
package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author CDT-Pranay
 *
 */

@JsonInclude(content = Include.NON_EMPTY, value = Include.NON_NULL)
public interface DataInsertionModel {
    Integer getId();

    Integer getBankId();

    String getName();

    String getIfsc();

    Integer getDistrictCode();

    Integer getStateCode();

    String getAddress();

    Integer getCommodityId();

    Integer getVarietyId();

    Integer getPhenophaseId();

    Integer getStartDas();

    Integer getEndDas();

//    Integer getStatus();

    Integer getAczId();

    Integer getSowingStartWeek();

    Integer getSowingEndWeek();

    Integer getHarvestStartWeek();

    Integer getHarvestEndWeek();

    Boolean getStatus();
}
