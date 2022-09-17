import { AgriActivity } from './AgriActivity';
import { AgriDoseFactor } from './AgriDoseFactor';
import { AgriStresSeverity } from './AgriStressSeverity';
export class PageAgriActivity {
    content : AgriActivity[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
