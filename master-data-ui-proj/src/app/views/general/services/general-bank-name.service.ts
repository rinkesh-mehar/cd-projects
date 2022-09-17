
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { GeneralBankName } from '../models/GeneralBankName';
import { environment } from '../../../../environments/environment';
import { PageGeneralBankName } from '../models/PageGeneralBankName';

@Injectable({
  providedIn: 'root'
})
export class GeneralBankNameService {

    // Base url
    baseUrl = environment.apiUrl+'/general/bank';
    maxSize: number = 10;

    constructor(private http: HttpClient) { }
  
    // Http Headers
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
  
    // POST
    CreateBankName(data): Observable<ResponseMessage> {
      return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }  
  
    // GET By ID
    GetBankName(id): Observable<GeneralBankName> {
      return this.http.get<GeneralBankName>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // GET
    GetAllBankName(): Observable<GeneralBankName> {
      return this.http.get<GeneralBankName>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

    //get bank category
    GetAllBankNameByBankCategory(bankCategoryId):Observable<GeneralBankName>{
      return this.http.get<GeneralBankName>(this.baseUrl + '/bankCategory-id/' + bankCategoryId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

     //For-Pagination
     getPageGeneralBankName(page: number,rowSize: number,searchText, isValid: number): Observable<PageGeneralBankName> {
      this.maxSize = rowSize || this.maxSize;
      var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText;
      return this.http.get<PageGeneralBankName>(url)
        .pipe(
          map(response => {
            const data = response;
            console.log(data.content);
            return data;
          }));
    }
  
    // PUT
    UpdateBankName(id, data): Observable<ResponseMessage> {
      return this.http.put<ResponseMessage>(this.baseUrl +  "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // DELETE
    DeleteBankName(id){
      return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

     // Reject
  RejectBankName(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeBankName(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveBankName(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  
    // Error handling
    errorHandl(error) {
       let errorMessage = '';
       if(error.error instanceof ErrorEvent) {
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
