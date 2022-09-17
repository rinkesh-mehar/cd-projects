import { JobApplication } from "./JobApplication";

export class PageJobApplication {
    content: JobApplication[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number ;
    first: boolean ;
    sort: string ;
    numberOfElements: number ;
}
