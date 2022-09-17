import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { FarmerProxyRelationType } from '../models/FarmerProxyRelationType';
import { environment } from '../../../../environments/environment';
import { PageFarmerProxyRelationType } from '../models/PageFarmerProxyRelationType';

@Injectable({
  providedIn: 'root'
})
export class FarmerProxyRelationTypeService {

     // Base url
     baseUrl = environment.apiUrl+'/farmer/proxy-relation-type';
     maxSize: number = 10;

     constructor(private http: HttpClient) { }
   
     // Http Headers
     httpOptions = {
       headers: new HttpHeaders({
         'Content-Type': 'application/json'
       })
     }
   
     // POST
     CreateProxyRelationType(data): Observable<ResponseMessage> {
       return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }  
   
     // GET By ID
     GetProxyRelationType(id): Observable<FarmerProxyRelationType> {
       return this.http.get<FarmerProxyRelationType>(this.baseUrl + '/' + id)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
   
     // GET
     GetAllProxyRelationType(): Observable<FarmerProxyRelationType> {
       return this.http.get<FarmerProxyRelationType>(this.baseUrl+'/list')
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
   
     // PUT
     UpdateProxyRelationType(id, data): Observable<ResponseMessage> {
       return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }
   
     // DELETE
     DeleteProxyRelationType(id){
       return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
     }


  // Reject
  RejectProxyRelationType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeProxyRelationType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveProxyRelationType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getProxyRelationTypePagenatedList(page: number, rowSize: number, searchText): Observable<PageFarmerProxyRelationType> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageFarmerProxyRelationType>(url)
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
