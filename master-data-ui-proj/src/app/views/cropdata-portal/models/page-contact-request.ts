import { ContactRequest } from "./contact-request";

export class PageContactRequest {

    content: ContactRequest[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number ;
    first: boolean ;
    sort: string ;
    numberOfElements: number ;

}
