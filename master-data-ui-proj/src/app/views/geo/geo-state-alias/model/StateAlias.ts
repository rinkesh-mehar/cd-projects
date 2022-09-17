import {Alias} from '../../geo-district-alias/model/Alias';
import {States} from './States';

export class StateAlias {

    States: States[];

    content: Alias[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number;
    first: boolean;
    sort: string;
    numberOfElements: number;
}
