import { PartnershipRequest } from "./partnership-request";

export class PagePartnershipRequest {
    content: PartnershipRequest[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number ;
    first: boolean ;
    sort: string ;
    numberOfElements: number ;
}
