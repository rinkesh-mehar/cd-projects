export interface KMLListModel{
    farmerName: string;
    mobileNumber: string;
    commodity: string;
    variety: string;
    village: string;
    region: string;
    state: string;
    taskId:string;
}
export interface ResponseData {
    statusCode:string;
    message:string;
    data:KMLListModel[];
}