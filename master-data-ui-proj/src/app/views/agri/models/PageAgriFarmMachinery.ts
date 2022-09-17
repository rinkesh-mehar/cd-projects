import { AgriFarmMachinery } from './AgriFarmMachinery';

import { AgriHsCode } from './AgriHsCode';

export class PageAgriFarmMachinery {
    content : AgriFarmMachinery[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}