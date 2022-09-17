import {PricingMspGroup} from './PricingMspGroup';

export class PagePricingMsp {
    content: PricingMspGroup[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number;
    first: boolean;
    sort: string;
    numberOfElements: number;
    totalNoOfRecords: number;
}
