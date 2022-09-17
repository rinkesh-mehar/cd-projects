export interface DrKrishiTechnical{
    taskid:string;
    caseid: string;
    name: string;
    village: string;
    district: string;
    region: string;
    state: string;
    expecteddate: string;
    primarynumber: string;
    secondarynumber: string;
}
export interface ResponseList {
    status:number;
    message:string;
    data:DrKrishiTechnical[]
}