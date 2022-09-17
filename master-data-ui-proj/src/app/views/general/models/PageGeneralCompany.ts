import { GeneralCompany } from './GeneralCompany';
import { GeneralCallingStatus } from './GeneralCallingStatus';
import { GeneralPackagingType } from "./GeneralPackagingType";

export class PageGeneralCompany {
    content : GeneralCompany[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
