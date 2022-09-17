import { RegionTaskTypeSpecifications } from "./RegionTaskTypeSpecifications";

export class PageRegionTaskTypeSpecification{

    content : RegionTaskTypeSpecifications[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}