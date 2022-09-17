import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError, from } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { AgriStressType } from '../models/AgriStressType';
import { PageAgriStressType } from '../models/PageAgriStressType';

@Injectable({
  providedIn: 'root'
})
export class AgriStressTypeService {
  GetAllBioticStress() {
  
  }
     // Base url
     baseUrl = environment.apiUrl+'/agri/stress-type';
     maxSize: number = 10;

     constructor(private http: HttpClient) { }
   
     // Http Headers
     httpOptions = {
       headers: new HttpHeaders({
         'Content-Type': 'application/json'
       })
     }
   
     // POST
     CreateStressType(data): Observable<ResponseMessage> {
       return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }  
   
     // GET By ID
     GetStressType(id): Observable<AgriStressType> {
       return this.http.get<AgriStressType>(this.baseUrl + '/' + id)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
  
   
     // GET
     GetAllStressType(): Observable<AgriStressType> {
       return this.http.get<AgriStressType>(this.baseUrl+'/list')
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
       }
   
     // PUT
     UpdateStressType(id, data): Observable<ResponseMessage> {
       return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
   
     // DELETE
     DeleteStressType(id){
       return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }

         // Reject
  RejectStressType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeStressType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveStressType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
   
    //For-Pagination
    getPageStressType(page: number, rowSize: number,searchText): Observable<PageAgriStressType> {
      this.maxSize = rowSize || this.maxSize;
      var url = this.baseUrl + "/paginatedList?page=" + page + "&size=" + this.maxSize  + "&searchText=" + searchText;
      return this.http.get<PageAgriStressType>(url)
        .pipe(
          map(response => {
            const data = response;
            console.log(data.content);
            return data;
          }));
    }
  
    
    getStressTypeIdByCommodityIdAndStressId(commodityId,stressId): Observable<AgriStressType> {
      return this.http.get<AgriStressType>(this.baseUrl + '/getStressTypeId/' + commodityId + '/' + stressId)
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