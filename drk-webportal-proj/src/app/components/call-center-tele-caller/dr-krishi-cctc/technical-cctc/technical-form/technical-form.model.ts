export interface DRKrishiTechnicalForm {
    caseId:            string;
    rightId:            string;
    cropinformation:   Cropinformation;
    irrigationdetails: Irrigationdetails;
    contactInformation: ContactInformation;
    rightInformation: RightInformation;
    fertilizer:        Fertilizer;
    seedtreatment:     Seedtreatment;
    remedialmeasure:   Remedialmeasure;
    //ndviimage:         Ndviimage;
    schedule:          Schedule;
    type:              string;
    currentYear:       number;
    currentWeek:       number;
    calender: Calender;
}

export interface Calender {
  minDate: string;
  maxDate: string;
}
export interface Cropinformation {
    crop:               string;
    variety:            string;
    croppingarea:       string;
    // season:             string;
    sowingweek:         number;
    seedsource:         string;
    seedsamplereceived: boolean;
    seedrate:           number;
    uom:                string;
    spacingrow:         number;
    spacingplant:       number;
}

export interface Fertilizer {
    typeofapplication: string;
    name:              string;
    uom:               string;
    dose:              string;
    splitdose:         string[];
    weekofapplication: number;
}

export interface Irrigationdetails {
    irrigationsource:    Irrigationmethod[];
    irrigationmethod:    Irrigationmethod[];
    numberofirrigations: number;
    weekofittigation:    string;
}

export interface ContactInformation {
  stateName: string;
  districtName: string;
  tehsilName: string;
  villageName: string;
  regionName: string;
  farmerName: string;
}

export interface RightInformation {
  currentQuantity: number;
  harvestDate: string;
  harvestWeek: string;
  allowableVarianceQtyPos: number;
  allowableVarianceQtyNeg: number;
}

export interface Irrigationmethod {
    id:   number;
    name: string;
}

export interface Ndviimage {
    pathname: string;
    images:   Irrigationmethod[];
    month: string;
}


export interface Remedialmeasure {
    agrochemicalname:  Irrigationmethod[];
    brand:             string;
    uom:               string;
    dose:              string;
    weekofapplication: string;
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
export interface Seedtreatment {
    seedtreatmentoption: boolean;
    agent:         string;
    insecticide:   string;
    uom:           string;
    dose:          string;
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
    deliverableQuantity: number;
    harvestedQuantity: number;
    rightId: string;
}
export interface ResponseMessage {
    status:number;
    message:string;
    data:DRKrishiTechnicalForm;
}
export interface ndviImages {
    Year:number;
    Week:number;
    url:string;
}
