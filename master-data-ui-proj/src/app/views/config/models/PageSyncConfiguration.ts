import { SyncConfiguration } from "./SyncConfiguration";

export class PageSyncConfiguration{
    content: SyncConfiguration[];
    totalPages : number;
    totalElements : number;
    last : boolean;
    size : number ;
    first : boolean ;
    sort : string ;
    numberOfElements : number ;
}
