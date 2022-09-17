import { FarmerMaritalStatus } from './FarmerMaritalStatus';
import { FarmerLoanPurpose } from './FarmerLoanPurpose';

import { FarmerLanguage } from './FarmerLanguage';

export class PageFarmerMaritalStatus{
    content : FarmerMaritalStatus[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}