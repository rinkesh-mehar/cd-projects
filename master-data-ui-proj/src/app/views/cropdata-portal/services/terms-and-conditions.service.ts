import { TermsAndConditions } from './../models/terms-and-conditions';
import { PageTermsAndConditions } from './../models/pageTermsAndConditions';
import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import {ResponseMessage} from '../../agri/models/ResponseMessage';


@Injectable({
    providedIn: 'root'
})
export class TermsAndConditionsService {

    // baseUrl = environment.apiUrl + '/get/news/list';
    baseUrls = environment.apiUrl + '/site/terms-and-conditions';

    maxSize: number = 10;

    constructor(private http: HttpClient) {
    }

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    getTermsAndConditionsListByPagination(page: number, rowSize: number, searchText): Observable<PageTermsAndConditions> {
        this.maxSize = rowSize || this.maxSize;
        let url = this.baseUrls + '/paginategList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
        return this.http.get<PageTermsAndConditions>(url)
            .pipe(
                map(response => {
                    const data = response;
                    return data;
                }));
    }


    getTermsAndConditionsById(id): Observable<TermsAndConditions> {
        return this.http.get<TermsAndConditions>(this.baseUrls + '/id' + '/' + id)
            .pipe(
                catchError(this.errorHandl)
            );
    }


    addTermsAndConditions(data: any, termsAndConditionsFile: any, privacyPolicyFile: any): Observable<ResponseMessage> {
        const formData: FormData = new FormData;
       if (termsAndConditionsFile != undefined){

           formData.append('termsAndConditionsFile', termsAndConditionsFile);
        
       }
       if (privacyPolicyFile != undefined){

        formData.append('privacyPolicyFile', privacyPolicyFile);
     
      }
        formData.append('data', JSON.stringify(data));
        return this.http.post<ResponseMessage>(this.baseUrls + '/add', formData)
            .pipe(
                catchError(this.errorHandl)
            );
    }

    updateTermsAndConditions(id, data, termsAndConditionsFile: any, privacyPolicyFile: any): Observable<ResponseMessage> {
        const formData: FormData = new FormData;
        if (termsAndConditionsFile != undefined){

            formData.append('termsAndConditionsFile', termsAndConditionsFile);
         
        }
        if (privacyPolicyFile != undefined){
 
         formData.append('privacyPolicyFile', privacyPolicyFile);
      
       }
        formData.append('data', JSON.stringify(data));
        return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id, formData)
            .pipe(
                retry(1),
                catchError(this.errorHandl)
            );
    }

     // PUT
     deactiveTermsAndConditions(id): Observable<any> {
        return this.http.put<ResponseMessage>(this.baseUrls + '/deactive/' + id  , this.httpOptions)
          .pipe(
            retry(1),
            catchError(this.errorHandl)
          )
    }
    // PUT
    activeTermsAndConditions(id): Observable<any> {
        return this.http.put<ResponseMessage>(this.baseUrls + '/active/' + id  , this.httpOptions)
          .pipe(
            retry(1),
            catchError(this.errorHandl)
          )
    }

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
