import { GeneralPoi } from './GeneralPoi';
import { GeneralModeOfPayment } from './GeneralModeOfPayment';
import { GeneralUom } from './GeneralUom';
export class PageGeneralPoi {
    content : GeneralPoi[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
