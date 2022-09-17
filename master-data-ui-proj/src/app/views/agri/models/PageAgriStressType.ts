import { AgriStressType } from './AgriStressType';
import { AgriStressControlRecommendation } from './AgriStressControlRecommendation';

export class PageAgriStressType{
    content : AgriStressType[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}