import { GeoState } from '../../logistic-hub/model/GeoState';


export class PageGeoState{
    content : GeoState[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}