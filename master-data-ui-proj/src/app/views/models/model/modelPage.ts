import { Models } from './Models';

export class ModelPage {

    content: Models[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number;
    first: boolean ;
    sort: string ;
    numberOfElements: number;
}