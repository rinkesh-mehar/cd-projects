export interface RltSampleReceivedModel{
    taskId:string;
    barcodeNumber:string;
    caseId:string;
    village: string;
    formerMobile: string;
    crop: string;
    receivedDate: string;
    reverseDate:string;
    cropType:number;
    sellerType:number;
}

export interface RtlResponse {
    message:string;
    statusCode:number;
    data:RltSampleReceivedModel
}


export interface BarcodeRequestModel {
    taskId:string;
    userId:number;
    date:Date;
}
