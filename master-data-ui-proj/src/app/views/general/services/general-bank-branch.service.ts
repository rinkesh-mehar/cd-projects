import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { GeneralBankBranch } from '../models/GeneralBankBranch';
import { GeneralBankNameService } from './general-bank-name.service';
import { PageGeneralBankBranch } from '../models/PageGeneralBankBranch';

@Injectable({
  providedIn: 'root'
})
export class GeneralBankBranchService {

    // Base url
    baseUrl = environment.apiUrl+'/general/bank-branch';
    maxSize: number = 10;

    constructor(private http: HttpClient,private generalBankNameService : GeneralBankNameService) { }
  
    // Http Headers
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
  
    // POST
    CreateBankBranch(data): Observable<ResponseMessage> {
      return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }  
  
    // GET By ID
    GetBankBranch(id): Observable<GeneralBankBranch> {
      return this.http.get<GeneralBankBranch>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // GET
    GetAllBankBranch(): Observable<GeneralBankBranch> {
      return this.http.get<GeneralBankBranch>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

    //get bank
    GetAllBankBranchByBankName(bankId):Observable<GeneralBankBranch>{
      return this.http.get<GeneralBankBranch>(this.baseUrl + '/bank-id/' + bankId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

          //For-Pagination
  getPageGeneralBankBranch(page: number, rowSize: number,searchText, isValid: number,missing): Observable<PageGeneralBankBranch> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageGeneralBankBranch>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
    }));
  }
  
    // PUT
    UpdateBankBranch(id, data): Observable<ResponseMessage> {
      return this.http.put<ResponseMessage>(this.baseUrl +  "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // DELETE
    DeleteBankBranch(id){
      return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

     // Reject
  RejectBankBranch(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeBankBranch(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveBankBranch(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  moveToMaster(id): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/move-to-master/' + id, this.httpOptions)
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
