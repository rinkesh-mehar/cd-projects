export class ZonalPhenophaseDuration{
    id :  number;
    stateCode : number;
    state : string;
    zonalCommodityId : number;
    zonalVarietyId : number;
    commodityId : number;
    // ZonalCommodity : string;
    varietyId : number;
    // aczID  : number;
    aczId : number;
    aczName : string;
    zonalCommodity : string;
    zcSowingWeekStart : number;
    zcSowingWeekEnd : number;
    zonalVariety : string;
    zvSowingWeekStart : number;
    zvSowingWeekEnd : number;
    startDas : number;
    endDas : number;
    isValid : boolean;
    seasonId : number;
    season : string;
    phenophaseId : number;
     phenophase : string;
    phenophaseStart : number;
    phenophaseEnd : number;
    phenophaseOrder : number;
    imageURL:String;

    status: string;
}