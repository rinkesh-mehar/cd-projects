import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import {ResponseMessage} from '../../agri/models/ResponseMessage';
import {Parameter} from '../models/parameter';

@Injectable({
    providedIn: 'root'
})

export class GstmEyeService {

    // Base url
    baseUrl = environment.apiUrl + '/gstm-eye';

    constructor(private httpClient: HttpClient) {

    }

    // Http Headers
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    addParameters(data): Observable<ResponseMessage> {

        return this.httpClient.post<ResponseMessage>(this.baseUrl + '/parameter', JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getParameterById(id): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/parameter?parameterId=' + id)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    /*Update Parameter*/
    updateParameter(id, data): Observable<ResponseMessage> {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/parameter/' + id, JSON.stringify(data), this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    /*Delete Parameter*/
    deleteParameter(id): Observable<ResponseMessage> {
        return this.httpClient.put<ResponseMessage>(this.baseUrl + '/parameter/delete/' + id, this.httpOptions)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }


    getParameterListByPagination(page: number, searchText): Observable<any> {
        console.log('pagination url is => ', this.baseUrl + '/parameter/Paginated/list' + '?page=' + page + '&size=' + environment.pageSize + '&searchText=' + searchText);
        const url = this.baseUrl + '/parameter/Paginated/list' + '?page=' + page + '&size=' + environment.pageSize + '&searchText=' + searchText;
        return this.httpClient.get<any>(url)
            .pipe(
                map(response => {
                    const data = response;
                    return data;
                }));
    }


    getParameterList(): Observable<any> {
        return this.httpClient.get<any>(this.baseUrl + '/parameter/list')
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getPlatform(): Observable<Parameter> {
        return this.httpClient.get<Parameter>(this.baseUrl + '/platform')
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getDataType(platformId): Observable<Parameter> {
        return this.httpClient.get<Parameter>(this.baseUrl + '/dataType?platformId=' + platformId)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getCategory(dataTypeId): Observable<Parameter> {
        return this.httpClient.get<Parameter>(this.baseUrl + '/category?dataTypeId=' + dataTypeId)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    getSubcategory(categoryId): Observable<Parameter> {
        return this.httpClient.get<Parameter>(this.baseUrl + '/subcategory?categoryId=' + categoryId)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    // Error handling
    errorHandle(error) {
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
