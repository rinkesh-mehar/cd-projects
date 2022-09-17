import {Calender, ContactInformation, Cropinformation, RightInformation} from '../technical-form/technical-form.model';

export interface HarvestMonitoringForm {

  // generalInformation: GeneralInformation;
  // rightDeatils: RightDeatils;
  caseId:            string;
  rightId:            string;
  cropinformation:   Cropinformation;
  contactInformation: ContactInformation;
  rightInformation: RightInformation;
  //ndviimage:         Ndviimage;
  schedule:          Schedule;
  type:              string;
  currentYear:       number;
  currentWeek:       number;
  calender: Calender;
}

export interface Schedule {
  primarynumber:    number;
  secondarynumber:  number;
  visitnotrequired: boolean;
  dateschedule: Date;
  visitrequired: boolean;
  callingstatus: string;
  comments: string;
}
export interface callingStatusModel {
  taskid: string;
  callingstatus: string;
}
export interface saveSchedule {
  userid:number;
  taskid:string;
  callingstatus: string;
  dateschedule: string[];
  visitrequired: boolean;
  comments: string;
  type:string;
}
export interface ResponseMessage {
  status:number;
  message:string;
  data:HarvestMonitoringForm;
}

export interface GeneralInformation {
  crop:string;
  variety: string;
  state:string;
  // season: string;
  region: number;
  lotCurrentTotalQuantity: number;
}

export interface RightDeatils {
  village: string;
  district: string;
  harvestDate:Date;
  state: string;
  currentQuantity: number;
  standardQuantity: number;
  allowableVarianceQtyPos: number;
  allowableVarianceQtyNeg: number;
  sowingWeek:number;
  harvestedQuantity:number;
  deliverableQuantity:number;
}

