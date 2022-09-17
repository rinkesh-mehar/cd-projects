import {Parameter} from './parameter';

export class PageParameter {
    content: Parameter[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number ;
    first: boolean ;
    sort: string ;
    numberOfElements: number ;
}
