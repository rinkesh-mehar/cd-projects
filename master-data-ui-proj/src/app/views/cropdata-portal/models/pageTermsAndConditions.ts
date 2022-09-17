import { TermsAndConditions } from './terms-and-conditions';
import {NewsReports} from './newsReports';


export class PageTermsAndConditions {
    content: TermsAndConditions[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number ;
    first: boolean ;
    sort: string ;
    numberOfElements: number ;
}
