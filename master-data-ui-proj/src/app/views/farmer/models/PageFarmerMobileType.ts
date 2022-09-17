import { FarmerMobileType } from './FarmerMobileType';
import { FarmerMaritalStatus } from './FarmerMaritalStatus';
import { FarmerLoanPurpose } from './FarmerLoanPurpose';

import { FarmerLanguage } from './FarmerLanguage';

export class PageFarmerMobileType{
    content : FarmerMobileType[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}