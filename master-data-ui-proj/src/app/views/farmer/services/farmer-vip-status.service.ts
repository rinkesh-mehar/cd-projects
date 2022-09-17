import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { FarmerVipStatus } from '../models/FarmerVipStatus';
import { environment } from '../../../../environments/environment';
import { PageFarmerVipStatus } from '../models/PageFarmerVipStatus';

@Injectable({
  providedIn: 'root'
})
export class FarmerVipStatusService {

     // Base url
     baseUrl = environment.apiUrl+'/farmer/vip-status';
     maxSize: number = 10;

     constructor(private http: HttpClient) { }
   
     // Http Headers
     httpOptions = {
       headers: new HttpHeaders({
         'Content-Type': 'application/json'
       })
     }
   
     // POST
     CreateVipStatus(data): Observable<ResponseMessage> {
       return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }  
   
     // GET By ID
     GetVipStatus(id): Observable<FarmerVipStatus> {
       return this.http.get<FarmerVipStatus>(this.baseUrl + '/' + id)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
   
     // GET
     GetAllVipStatus(): Observable<FarmerVipStatus> {
       return this.http.get<FarmerVipStatus>(this.baseUrl+'/list')
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
   
     // PUT
     UpdateVipStatus(id, data): Observable<ResponseMessage> {
       return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
   
     // DELETE
     DeleteVipStatus(id){
       return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }


  // Reject
  RejectVipStatus(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeVipStatus(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveVipStatus(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getVipStatusPagenatedList(page: number, rowSize: number, searchText): Observable<PageFarmerVipStatus> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageFarmerVipStatus>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
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
