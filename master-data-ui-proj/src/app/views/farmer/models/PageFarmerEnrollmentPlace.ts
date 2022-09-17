import { FarmerEnrollmentPlace } from './FarmerEnrollmentPlace';
import { FarmerEducation } from './FarmerEducation';

import { FarmerLanguage } from './FarmerLanguage';

export class PageFarmerEnrollmentPlace{
    content : FarmerEnrollmentPlace[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}