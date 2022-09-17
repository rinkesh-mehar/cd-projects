import { GeneralTypeOfService } from './GeneralTypeOfService';
import { GeneralCompany } from './GeneralCompany';
import { GeneralCallingStatus } from './GeneralCallingStatus';
import { GeneralPackagingType } from "./GeneralPackagingType";

export class PageGeneralTypeOfService {
    content : GeneralTypeOfService[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
