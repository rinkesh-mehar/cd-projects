import {Alias} from './Alias';
import {Commodity} from './Commodity';

export class CommodityAlias {

    Commodity: Commodity[];

    content : Alias[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
