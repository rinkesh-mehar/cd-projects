export interface Rlt {

    caseid:number;
    crop :number;
    variety :number;
    cultivation :number;
    season :string;
    phenophase :number;
    sowing_date :string;
    seed :number ;
	seedrate :number ;
    sample:string;
    incidenceone:string;
    severityone:string;
    incidencetwo:string;
    severitytwo:string;
    incidencethree:string;
    severitythree:string;
    stressone:string;
    stresstwo:string;
    stressthree:string;
    sampleTwo:string;
    sampleThree:string;
    foundin:string;
    notificationname:string;
    spotheading:string;
}
export interface SpotData {
    spots: Spot[];
}

export interface Spot {
    name:     string;
    stresses: Stress[];
    health:  Health;
}

export interface Health {
    left:      HealthFront;
    front:     HealthFront;
    right:     HealthFront;
    questions: Question[];
}

export interface HealthFront {
    image: Image;
}

export enum Image {
    ImageeditorJpg = "imageeditor.jpg",
}

export interface Question {
    formControlName: string;
    name:     string;
    type:     string;
    values?:  string[];
    selected: string;
}

export interface Stress {
    name:  string;
    desc: string;
    type:  string;
    left:  Front;
    front: Front;
    right: Front;
    status: boolean;
}

export interface Front {
    incidence: string;
    severity:  string;
    image:     string;
}
export interface RecommendationData {
    severity_value: string;
    severity_type:  string;
    label:          string;
    stresses:       RecommeddationStress[];
}

export interface RecommeddationStress {
    id?:            string;
    name:           string;
    severity:       string;
    recommendation: Recommendation[];
}

export interface Recommendation {
    id:          string;
    instruction: string;
}

export interface AgriQualityParameterList {
  parameterID: string;
  value: string;
}

export interface CommodityInfo {
  commodityId: number;
  zonalCommodityId: number;
}
