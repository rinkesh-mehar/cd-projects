export interface HarvestConfirmationListModel{
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
}
export interface callingStatusModel {
  taskid: string;
  userid:number;
  callingstatus: string;
}

export interface filterValueModel {
  stateId: number;
  regionId: number;
  districtId: number;
  villageId: number;
}

export interface ResponseMessage {
  status:number;
  message:string;
}
export interface SubmitNontechincalCallingStatus {
  callingstatus: number;
}
