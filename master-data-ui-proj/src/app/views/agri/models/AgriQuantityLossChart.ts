export class AgriQuantityLossChart{
    id : number;
    commodityId : number;
    commodity : string;
    stressId : number;
    stress : string;
    phenophaseId : number;
    phenophase : string;
    minPercentYieldCorrection : number;
    maxPercentYieldCorrection : number;
    avgSeverityBandMin : number;
    avgSeverityBandMax : number;
    // criteriaId : number;
    // criteria : string;
    status: string;
    minQuantityCorrectionPercent : number;
    maxQuantityCorrectionPercent : number;
    maxBandValue : number;
    minBandValue : number;
}