package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author RinkeshKM
 * @Date 29/01/21
 */

@Data
@AllArgsConstructor
public class AgriCommodityStressQuantityC {
    private int Id;
    private int commodityId;
    private int stressId;

    @Override
    public String toString() {
        return Id + "," + commodityId + "," + stressId;
    }
}
