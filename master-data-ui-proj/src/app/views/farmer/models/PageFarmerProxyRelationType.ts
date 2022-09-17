import { FarmerProxyRelationType } from './FarmerProxyRelationType';
import { FarmerIncomeSource } from './FarmerIncomeSource';
import { FarmerIdProof } from './FarmerIdProof';
import { FarmerGovtDepartment } from './FarmerGovtDepartment';
import { FarmerEnrollmentPlace } from './FarmerEnrollmentPlace';
import { FarmerEducation } from './FarmerEducation';

import { FarmerLanguage } from './FarmerLanguage';

export class PageFarmerProxyRelationType{
    content : FarmerProxyRelationType[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}