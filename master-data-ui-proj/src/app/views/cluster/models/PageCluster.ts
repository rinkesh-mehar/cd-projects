
import { Cluster } from './Cluster';

export class PageCluster{
    content : Cluster[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}