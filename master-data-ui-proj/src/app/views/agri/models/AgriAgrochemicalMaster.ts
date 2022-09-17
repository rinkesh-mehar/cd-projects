export class AgriAgrochemicalMaster {
    id: number;
    agrochemicalType: string;
    agrochemicalTypeId: number;
    name: string;
    cibrcApproved: string;
    waitingPeriod: number;
    commodityId: number;
    commodity : string;
    stressTypeId: number;
    stressType : string;
    stressNameList: Array<any>;
    status: string;
    agrochemicalId : number;
}
