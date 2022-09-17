import { ZonalVariety } from '../../regional/models/ZonalVariety';
export class ZonalPlantHealthIndex{

    id: number;
    name : string;
    stateCode : number;
    commodityID : number;
    phenophaseId : number;
    state :string;
    commodity :string;
    variety :string;
    phenophase :string;
    normalValue : number;
    idealValue : number;
    healthParameterId:number;
    // value : number;
    status :string;
    zonalCommodityId : number;
    zonalVarietyId: Number;
    zonalCommodity : string;
    zcSowingWeekStart : number;
    zcSowingWeekEnd : number;
    zonalVariety : string;
    zvSowingWeekStart : number;
    zvSowingWeekEnd : number;
    stateName :string;
    aczName :string;
    phenophaseName :string;
    specifications: number;

}