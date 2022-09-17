import {Injectable} from '@angular/core';
import {Districts} from '../model/Districts';

@Injectable({
    providedIn: 'root'
})
export class StoreService {

    constructor() {
    }

    storeDistrict = [];

    addToStore(districts: Districts[]) {
        this.storeDistrict.push(districts);
    }

    getToDistrict() {
        return this.storeDistrict;
    }
}
