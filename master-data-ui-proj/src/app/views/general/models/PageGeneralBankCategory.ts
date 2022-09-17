import { GeneralBankCategory } from './GeneralBankCategory';
import { GeneralPackagingType } from "./GeneralPackagingType";

export class PageGeneralBankCategory {
    content : GeneralBankCategory[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
