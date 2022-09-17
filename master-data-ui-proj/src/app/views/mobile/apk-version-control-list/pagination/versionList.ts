
import {AppVersionDetails} from '../../AppVersionDetails';

export class VersionList {

    content: AppVersionDetails[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number ;
    first: boolean ;
    sort: string ;
    numberOfElements: number ;
}
