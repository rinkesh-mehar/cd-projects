export interface DiagnoseDetails {
    rlStressImagePath: string;
    rlHealthImagePath: string;
    spots: Spot[];
    masterData: MasterData;
}

export interface QualityAssurance {
    value: any;
    parameterId: any;
    commodityId: any;
    maxValue: any;
    minValue: any;
    name: any;
}

export interface MasterData {
    stressDetails: StressDetail[];
    stressSeverity: StressSeverity[];
    stressSymptomsModel: Symptom[];
}

export interface StressDetail {
    stressId: number;
    stressName: string;
    stressTypeId: number;
    stressTypeName: string;
}

export interface StressSeverity {
    id: number;
    name: string;
    start: number;
    end: number;
    status: number;
}

export interface StressSymptomsModel {
    stressId: number;
    symptoms: Symptom[];
}

export interface Symptom {
    symptomsId: number;
    symptomsDesc: string;
}


export interface Spot {
    userId: number;
    name: string;
    stresses: SpotStress[];
    health: Health;
    isSpotBenchmark: boolean;
}

export interface SpotStress {
    id: number;
    stressId: number;
    desc: number;
    type: string;
    left: StressFront;
    front: StressFront;
    right: StressFront;
    status: boolean;
}

export interface StressFront {
    benchmark:boolean;
    incidence: Incidence;
    severity: Severity;
    image: Image;
}


export enum Incidence {
    Incidence1 = "Incidence 1",
    Incidence2 = "Incidence 2",
    Incidence3 = "Incidence 3",
}

export enum Severity {
    Severity1 = "Severity 1",
    Severity2 = "Severity 2",
    Severity3 = "Severity 3",
}


export enum Image {
    AssetsImgImageeditorJpg = "../../../assets/img/imageeditor.jpg",
    AssetsImgImgitemJpg = "../../../assets/img/imgitem.jpg",
}




export interface Health {
    left: string;
    front: string;
    right: string;
    questions: Question[];
}

export interface Question {
    formControlName: string;
    name: string;
    type: string;
    values?: string[];
    selected: string;
}









export interface ResultsMaster {
    userId:number;
    severityValue: string;
    severityType: string;
    label: string;
    comment: string;
    taskId: number;
    stresses: ResultsStress[];
}

export interface ResultsStress {
    id?: string;
    name: string;
    severity: string;
    recommendations: Recommendation[];
    selectedRecommendations:number[];

}

export interface Recommendation {
    id: string;
    instruction: string;
}






export interface GeneralInfoMaster {
    generalInformation: GeneralInfos;
    rlAerialImagePath: string;
    ariealPhotos: string[];
    comments: Comment[];
}


export interface GeneralInfos {
    state: String;
    tehsil: String;
    distict: String;
    villageName: String;
    farmerName: String;
    farmerMobileNumber: number;
    cropName: any;
    stateCode: number;
}


export interface SymptomMasterList {
    symptomsId: number;
    symptomsDesc: string;
}
