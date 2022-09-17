export interface DrKrishiNonTechnical{
    name: string;
    village: string;
    district: string;
    region: string;
    state: string;
    primarynumber: number;
    secondarynumber: number;
    type:string;
    taskid :number;
    sellerType:string; //added for seller type -CDT-Ujwal
    cropType:string;
    commodityName:string;
    cropArea:number;
    cropTypeId:number;
}
export interface callingStatusModel {
    taskid: string;
    userid:number;
    callingstatus: string;
}

export interface filterValueModel {
    areaId: number;
    stateId: number;
    regionId: number;
    districtId: number;
    villageId: number;
    commodityId: number;
}

export interface ResponseMessage {
    status:number;
    message:string;
}
export interface SubmitNontechincalCallingStatus {
    callingstatus: number;
}
