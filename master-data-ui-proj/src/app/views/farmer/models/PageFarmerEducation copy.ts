import { FarmerFarmOwnershipType } from './FarmerFarmOwnershipType';
import { FarmerEducation } from './FarmerEducation';

import { FarmerLanguage } from './FarmerLanguage';

export class PageFarmerFarmOwnershipType{
    content : FarmerFarmOwnershipType[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}