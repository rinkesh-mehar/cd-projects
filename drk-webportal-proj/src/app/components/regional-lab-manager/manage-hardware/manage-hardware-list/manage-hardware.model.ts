export interface ManageHarwareModel{
    id: number;
    name: string;
    mobilenumber: number;
    date: string;
    role: string;
    vanid: string;
    boxid: string;
    reverseDate:string;
}

export interface ResponseMessage {
    statusCode:number;
    message:string;
    data:ManageHarwareModel[];
}