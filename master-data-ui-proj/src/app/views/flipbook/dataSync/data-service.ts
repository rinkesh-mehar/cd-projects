import { EventEmitter, Injectable, Output, Directive } from '@angular/core';

@Directive()
@Injectable({
    providedIn: 'root'
})
export class DataService {
    // sending data from CLSO Comp to Carousel
    @Output() dataSync: EventEmitter<boolean> = new EventEmitter();
    @Output() metaSync: EventEmitter<boolean> = new EventEmitter();
    @Output() queSync: EventEmitter<boolean> = new EventEmitter();

    // sending data from  Carousel to CLSO
    @Output() outPut: EventEmitter<boolean> = new EventEmitter();

    // Sending data from CLSO to Carousel
    sendData(symptom: any) {
        this.dataSync.emit(symptom);
    }
    sendMeta(_meta: any) {
        this.metaSync.emit(_meta);
    }
    sendQue(_meta: any) {
        this.queSync.emit(_meta);
    }
    // Sending data from Carousel to CLSO
    _op(_op: any) {
        this.outPut.emit(_op);
    }

}
