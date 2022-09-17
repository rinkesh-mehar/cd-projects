import { GeoCountry } from './GeoCountry';

import { GeoTehsil } from './GeoTehsil';

export class PageGeoCountry{
    content : GeoCountry[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}