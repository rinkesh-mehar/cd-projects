
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { GeneralBankCategory } from '../models/GeneralBankCategory';
import { environment } from '../../../../environments/environment';
import { PageGeneralBankCategory } from '../models/PageGeneralBankCategory';

@Injectable({
  providedIn: 'root'
})
export class GeneralBankCategoryService {

      // Base url
      baseUrl = environment.apiUrl+'/general/bank-category';
      maxSize: number = 10;

      constructor(private http: HttpClient) { }
    
      // Http Headers
      httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      }
    
      // POST
      CreateBankCategory(data): Observable<ResponseMessage> {
        return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }  
    
      // GET By ID
      GetBankCategory(id): Observable<GeneralBankCategory> {
        return this.http.get<GeneralBankCategory>(this.baseUrl + '/' + id)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }
    
      // GET
      GetAllBankCategory(): Observable<GeneralBankCategory> {
        return this.http.get<GeneralBankCategory>(this.baseUrl+'/list')
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }
  
  
    //For-Pagination
    getPageGeneralBankCategory(page: number,rowSize: number,searchText): Observable<PageGeneralBankCategory> {
      this.maxSize = rowSize || this.maxSize;
      var url = this.baseUrl + "/paginatedList?page=" + page + "&size=" + this.maxSize + "&searchText=" + searchText;
      return this.http.get<PageGeneralBankCategory>(url)
        .pipe(
          map(response => {
            
            const data = response;
            //console.log(data.content);
            return data;
          }));
    }
    
      // PUT
      UpdateBankCategory(id, data): Observable<ResponseMessage> {
        return this.http.put<ResponseMessage>(this.baseUrl +  "/" + id + '/update', JSON.stringify(data), this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }
    
      // DELETE
      DeleteBankCategory(id){
        return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
      }
  
       // Reject
    RejectBankCategory(id) {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }
  
    //  Finalize
    FinalizeBankCategory(id) {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }
  
  
    // Approve
    ApproveBankCategory(id) {
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
