import { GeneralCallingStatus } from './GeneralCallingStatus';
import { GeneralPackagingType } from "./GeneralPackagingType";

export class PageGeneralCallingStatus {
    content : GeneralCallingStatus[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
