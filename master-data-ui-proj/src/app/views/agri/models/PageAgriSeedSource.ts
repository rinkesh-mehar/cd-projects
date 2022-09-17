import { AgriSeason } from './AgriSeason';
import { AgriIrrigationMethod } from './AgriIrrigationMethod';
import { AgriStress } from "./AgriStress";

export class PageAgriSeedSource {
    content : AgriSeason[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
