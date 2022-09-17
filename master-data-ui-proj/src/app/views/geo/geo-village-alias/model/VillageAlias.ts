import {Alias} from '../../geo-tehsil-alias/model/Alias';
import {Villages} from './Villages';

export class VillageAlias {

    Villages: Villages[];

    content: Alias[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number;
    first: boolean;
    sort: string;
    numberOfElements: number;
}