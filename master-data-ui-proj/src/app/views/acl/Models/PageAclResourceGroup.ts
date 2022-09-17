import { ResourceGroup } from './resource-group';

export class PageAclResourceGroup {
    content : ResourceGroup[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
