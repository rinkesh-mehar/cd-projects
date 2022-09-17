export interface RegionalLabData {
    rlStressImagePath: string;
    rlHealthImagePath: string;
    rlAerialImagePath: string;
    ariealPhotos: string[];
    shapeFile: string;
    comments: Comment[];
    results: Results;
    spots: Spot[];
    masterData: MasterData;
    generalInformation: GeneralInformation;
}

export interface GeneralInformation {
    state: String;
    tehsil: String;
    distict: String;
    villageName: String;
    farmerName: String;
    farmerMobileNumber: number;
}

export interface MasterData {
    stressDetails: StressDetail[];
    stressSeverity: StressSeverity[];
    stressSymptomsModel: StressSymptomsModel[];
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

export interface Comment {
    name: string;
    designation: string;
    dateTime: string;
    comment: string;
}










export interface Results {
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












export interface Spot {
    userId: number;
    name: string;
    isSpotBenchmark: boolean;
    stresses: SpotStress[];
    health: Health;
}

export interface Health {
    left: string;
    front: string;
    right: string;
    questions: Question[];
}

export interface HealthFront {
    image: Image;
}

export enum Image {
    AssetsImgImageeditorJpg = "../../../assets/img/imageeditor.jpg",
    AssetsImgImgitemJpg = "../../../assets/img/imgitem.jpg",
}

export interface Question {
    formControlName: string;
    name: string;
    type: string;
    values?: string[];
    selected: string;
}

export interface SpotStress {
    stressId: number;
    desc: number;
    type: string;
    left: StressFront;
    front: StressFront;
    right: StressFront;
    status: boolean;
}

export interface StressFront {
    id: string;
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

export interface CropInfo {
    commodityName: string;
    cropArea: number;
    seasonName: string;
    seedSourceName: string;
    seedsRates: number;
    seedsSampleReceived: boolean;
    sowingWeek: string;
    sowingYear: number;
    spacingPlantToPlant: number;
    cropTypeName: string;
    sellerGivenQtyTon: number;
    spacingRowToRow: number;
    taskId: null;
    // unitOFMeasurement: string;
    varietyName: string;
}

export interface IrrigationDetails {
    irrigationSource: string[];
    irrigationMethod: string[];
    numberOfIrrigation: number;
    weekOfIrrigationl: string;
}

export interface FertilizerDetails {
    applicationType: String;
    fertilizerName: String;
    fertilizerUom: String;
    fertilizerDose: String;
    splitDose: String;
    applicationWeek: string;
}

export interface SeedTreatment {
    seedTreatment: boolean;
    seedTreatmentAgent: String[];
    seedTreatmentUom: String;
    seedTreatmentAgentDose: String;
}

export interface SeedTreatment {
    agrochemicalName: String;
    remedialBrand: String;
    remedialDose: String;
    remedialUom: number;
    remedialWeek: number;
}

export interface RltUser{
    rltId: number;
    rltName: String;
}

export interface RlmReassign{
    rlmUserId:number
    taskId : number;
	rltUserId: number;
	comment: String;
}
