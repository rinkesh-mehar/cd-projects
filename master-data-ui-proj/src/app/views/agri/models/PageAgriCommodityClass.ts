import { AgriCommodityClass } from './AgriCommodityClass';

import { AgriHsCode } from './AgriHsCode';

export class PageAgriCommodityClass {
    content : AgriCommodityClass[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}