import { Timestamp } from 'rxjs/Rx';

export interface ServerResponse{
    header : Header;
    status : StatusMessage;
    data: User;
    error : ErrorMessage;

}

export interface Header{
    token : string;
}

export interface StatusMessage{
    id : string;
    detailMessage : string;
}

export interface ErrorMessage{
    errorId : string;
    errorMessage : string;
}

export interface User{
    User_id : BigInteger;
    User_Status: BigInteger;
    User_Password: string;
    Created_By: string;
    Created_Date_Time: Date;
    Modified_by: string;
    Modified_Date_Time: Date;
    Is_OTP: BigInteger;
    Password_Reset_Date: Date;
    System_Password_expired: BigInteger;
    stateId:number;
}



