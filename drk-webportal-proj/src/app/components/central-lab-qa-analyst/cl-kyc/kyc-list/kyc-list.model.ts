export interface KycList{
    taskId:String;
    farmerId: string;
    farmerName: string;
    mobileNumber: string;
    commodity: string;
    village: string;
    region: string;
    state: string;
}
export interface ResponseData {
    statusCode:string;
    message:string;
    data:KycList[]
}
