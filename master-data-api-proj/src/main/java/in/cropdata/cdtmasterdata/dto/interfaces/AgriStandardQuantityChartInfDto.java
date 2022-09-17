package in.cropdata.cdtmasterdata.dto.interfaces;

public interface AgriStandardQuantityChartInfDto {

    Integer getID();

    Integer getStateCode();

    String getState();

    Integer getCommodityID();

    String getCommodity();

    Integer getVarietyID();

    String getVariety();

    Double getStandardQuantityPerAcre();

    Double getStandardPositiveVariancePercent();

    Double getStandardNegativeVariancePercent();

    Double getStandardPositiveVariancePerAcre();

    Double getStandardNegativeVariancePerAcre();

    String getStatus();

    Boolean getIsValid();

}
