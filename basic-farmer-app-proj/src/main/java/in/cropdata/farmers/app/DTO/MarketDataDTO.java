package in.cropdata.farmers.app.DTO;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)	
public interface MarketDataDTO {

    Double getMinPrice();
    
    Double getMaxPrice();
    
    Double getModalPrice();
    
    String getMarketName();
	
    Date getArrivalDate();
    
    String getVarietyName();
    
    Double getMinPriceChangePercent();
    
    Double getMaxPriceChangePercent();
    
    Double getModalPriceChangePercent();
    
}
