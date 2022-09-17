import { FarmerMaritalStatus } from './FarmerMaritalStatus';
import { FarmerLoanPurpose } from './FarmerLoanPurpose';

import { FarmerLanguage } from './FarmerLanguage';
import { FarmerVipStatus } from './FarmerVipStatus';

export class PageFarmerVipStatus{
    content : FarmerVipStatus[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}