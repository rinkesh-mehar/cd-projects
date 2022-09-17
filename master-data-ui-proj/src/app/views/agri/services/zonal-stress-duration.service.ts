import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { ResponseMessage } from '../models/ResponseMessage';
import { retry, catchError, map } from 'rxjs/operators';
import { ZonalStressDuration } from '../models/ZonalStressDuration';
import { PageZonalStressDuration } from '../models/PageZonalStressDuration';

@Injectable({
    providedIn: 'root'
})
export class ZonalStressDurationService {

    // Base url
    baseUrl = environment.apiUrl + '/zonal/stress-duration';
    stressBaseUrl = environment.apiUrl + '/agri/stress';
    maxSize: number = 10;

    constructor(private http: HttpClient) { }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    }

    // POST
    CreateZonalStressDuration(data): Observable<ResponseMessage> {
        // let formData = new FormData;
        // if (file) {
        //     formData.append("image", file);
        // }
        // formData.append("data", JSON.stringify(data));



        return this.http.post<ResponseMessage>(this.baseUrl + '/add', data, this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    //  GET By ID
    GetStress(id): Observable<ZonalStressDuration> {
        return this.http.get<ZonalStressDuration>(this.baseUrl + '/' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }


    //Get
    GetAllStressByStateCode(stateCode): Observable<ZonalStressDuration> {
        return this.http.get<ZonalStressDuration>(this.baseUrl + '/state-stateCode/' + stateCode)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    //Get
    GetAllStressByCommodityId(commodityId): Observable<ZonalStressDuration> {
        return this.http.get<ZonalStressDuration>(this.baseUrl + '/commodity-id/' + commodityId)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    //Get
    getAllStressByCommodityId(commodityId): Observable<ZonalStressDuration> {
        return this.http.get<ZonalStressDuration>(this.baseUrl + '/commodity/getStressList?commodityId=' + commodityId)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    //Get
    GetAllStressByStressTypeId(commodityId, stressTypeId): Observable<ZonalStressDuration> {
        return this.http.get<ZonalStressDuration>(this.baseUrl + '/' + commodityId + '/' + stressTypeId + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    getAllStressByCommoditiesId(_cmd: number, _phn: number): Observable<ZonalStressDuration> {
        return this.http.get<ZonalStressDuration>(this.baseUrl + '/' + _cmd + '/' + _phn)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    // GET
    GetAllStress(): Observable<ZonalStressDuration> {
        // return this.http.get<AgriStress>(this.baseUrl + '/list')
        return this.http.get<ZonalStressDuration>(this.stressBaseUrl + '/list')
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    // getAgrochemicalMasterByCommodityIdAndStressTypeId(commodityId, stressTypeId): Observable<AgriStress> {
    //     return this.http.get<AgriStress>(this.baseUrl + '/' + commodityId + + stressTypeId + '/')
    //       .pipe(
    //         retry(1),
    //         catchError(this.errorHandl)
    //       )
    //   }

    getPageZonalStressDuration(page: number, rowSize: number, searchText, isValid: number): Observable<PageZonalStressDuration> {
        this.maxSize = rowSize || this.maxSize;
        var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText;
        return this.http.get<PageZonalStressDuration>(url)
            .pipe(
                map(response => {
                    const data = response;
                    //console.log(data.content);
                    return data;
                }));
    }

    // PUT
    UpdateZonalStressDuration(id, data): Observable<ResponseMessage> {
        // alert(id);
        // let formData = new FormData;
        // if (file) {
        //     formData.append("image", file);
        // }
        // formData.append("data", JSON.stringify(data));

        return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', data, this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    // DELETE
    DeleteStress(id) {
        return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    // Reject
    RejectStress(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    //  Finalize
    FinalizeStress(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }


    // Approve
    ApproveStress(id) {
        return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            )
    }

    // GetSymptomsList(): Observable<any> {
    //     return this.http.get<ResponseMessage>(this.baseUrl + "/flipbook-symptoms/list", this.httpOptions)
    //         .pipe(
    //             retry(1),
    //             catchError(this.errorHandl)
    //         )
    // }



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
