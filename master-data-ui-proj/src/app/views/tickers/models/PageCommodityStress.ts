import {Commodity} from './commodity';

export class PageCommodityStress {
    content: Commodity[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number;
    first: boolean;
    sort: string;
    numberOfElements: number;
}
