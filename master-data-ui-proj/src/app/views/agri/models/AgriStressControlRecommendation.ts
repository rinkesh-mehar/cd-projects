export class AgriStressControlRecommendation{
    id : number;
    commodityId : number;
    commodity : string;
    stressControlMeasureId : number;
    stressControlMeasure : string;
    stressTypeId : number;
    stressType : string;
    stressId : number;
    stress : string;
    instructions : string;
    agrochemicalId : number;
    agrochemical : string;
    dosePerHectare : number;
    perHectareUomId : number;
    perHectareUOM : string;
    dosePerAcre : number;
    perAcreUomId : number;
    perAcreUOM : string;
    minWaterPerHectare : number;
    maxWaterPerHectare : number;
    perHectareWaterUomId : number;
    perHectareWaterUOM : string;
    minWaterPerAcre : number;
    maxWaterPerAcre : number;
    perAcreWaterUomId : number;
    perAcreWaterUOM : string;
    agrochemApplicationId : number;
    agroApplicationType: string;
    agroChemicalInstructions : string;
    status: string;
    waterPerHectare: number;
    waterPerAcre: number;
    state: string;
    district: string;
    aczName: string;
	zonalCommodity: string;
	zcSowingWeekStart: number;
	zcSowingWeekEnd: number;
	zonalVariety: number;
	zvSowingWeekStart: number;
    zvSowingWeekEnd: number;
    agrochemApplicationTypeId:number;
}