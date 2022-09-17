import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {retry, catchError, map} from 'rxjs/operators';
import {ResponseMessage} from '../models/ResponseMessage';
import {environment} from '../../../../environments/environment';
import {AgriAgrochemicalMaster} from '../models/AgriAgrochemicalMaster';
import {AgriAgrochemicalTypeService} from './agri-agrochemical-type.service';
import {ZonalStressDuration} from '../models/ZonalStressDuration';
import {AgriCommodityService} from './agri-commodity.service';
import {PageAgriAgrochemicalMaster} from '../models/PageAgriAgrochemicalMaster';

@Injectable({
    providedIn: 'root'
})
export class AgriCommodityAgrochemicalService {

    // Base url
    baseUrl = environment.apiUrl + '/agri/commodity/agrochemical';
    maxSize: number = 10;

    constructor(private http: HttpClient, agriAgrochemicalTypeService: AgriAgrochemicalTypeService,
                commodityService: AgriCommodityService) {
    }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    // POST
    CreateAgrochemicalMaster(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // GET By ID
    GetAgrochemicalMaster(id): Observable<AgriAgrochemicalMaster> {
        return this.http.get<AgriAgrochemicalMaster>(this.baseUrl + '/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // GET
    GetAllAgrochemicalMaster(): Observable<AgriAgrochemicalMaster> {
        return this.http.get<AgriAgrochemicalMaster>(this.baseUrl + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //For-Pagination
    getPageAgriAgrochemicalMaster(page: number, rowSize: number, searchText, isValid: number): Observable<PageAgriAgrochemicalMaster> {
        this.maxSize = rowSize || this.maxSize;    
        var url = this.baseUrl + '?page=' + page + '&size=' + this.maxSize + '&isValid=' + isValid + '&searchText=' + searchText;
        return this.http.get<PageAgriAgrochemicalMaster>(url)
            .pipe(
                map(response => {
                    const data = response;
                    console.log(data.content);
                    return data;
                }));
    }

    GetAllAgrochemicalMasterByAgrochemicalTypeId(agrochemicalTypeId): Observable<AgriAgrochemicalMaster> {
        return this.http.get<AgriAgrochemicalMaster>(this.baseUrl + '/agrochemical-type/' + agrochemicalTypeId)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    GetAllAgrochemicalMasterByCommodityId(commodityId): Observable<AgriAgrochemicalMaster> {
        return this.http.get<AgriAgrochemicalMaster>(this.baseUrl + '/commodity/' + commodityId)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    getAgrochemicalMasterByCommodityIdAndStressTypeId(commodityId, stressTypeId): Observable<ZonalStressDuration> {
        return this.http.get<ZonalStressDuration>(this.baseUrl + '/' + commodityId + '/' + stressTypeId + '/')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // PUT
    UpdateAgrochemicalMaster(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //Get
    GetAllAgrochemicalByStressTypeId(commodityId, stressTypeId): Observable<AgriAgrochemicalMaster> {
        return this.http.get<AgriAgrochemicalMaster>(this.baseUrl + '/' + commodityId + '/' + stressTypeId + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

     //Get
     findAgriAgrochemicalMasterByCommodityId(commodityId): Observable<AgriAgrochemicalMaster> {
        return this.http.get<AgriAgrochemicalMaster>(this.baseUrl + '/' + commodityId  + '/clist')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // DELETE
    DeleteAgrochemicalMaster(id) {
        return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // Reject
    RejectAgrochemicalMaster(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    //  Finalize
    FinalizeAgrochemicalMaster(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }


    // Approve
    ApproveAgrochemicalMaster(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

      // GET
      getAgrochemicalList(): Observable<AgriAgrochemicalMaster> {
        return this.http.get<AgriAgrochemicalMaster>(this.baseUrl + '/agrochemical-list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

    // Error handling
    errorHandl(error) {
        let errorMessage = '';
        if (error.error instanceof ErrorEvent) {
            // Get client-side error
            errorMessage = error.error.message;
        } else {
            // Get server-side error
            errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }
        console.log(errorMessage);
        return throwError(errorMessage);
    }
}
