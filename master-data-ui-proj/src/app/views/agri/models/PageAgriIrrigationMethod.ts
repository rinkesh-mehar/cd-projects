import { AgriIrrigationMethod } from './AgriIrrigationMethod';
import { AgriStress } from "./AgriStress";

export class PageAgriIrrigationMethod {
    content : AgriIrrigationMethod[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
