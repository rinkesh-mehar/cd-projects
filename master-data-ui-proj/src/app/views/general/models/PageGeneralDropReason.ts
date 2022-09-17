import { GeneralDropReason } from './GeneralDropReason';
import { GeneralCompany } from './GeneralCompany';
import { GeneralCallingStatus } from './GeneralCallingStatus';
import { GeneralPackagingType } from "./GeneralPackagingType";

export class PageGeneralDropReason {
    content : GeneralDropReason[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
