import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { FarmerIdProof } from '../models/FarmerIdProof';
import { environment } from '../../../../environments/environment';
import { PageFarmerIdProof } from '../models/PageFarmerIdProof';

@Injectable({
  providedIn: 'root'
})
export class FarmerIdProofService {

    // Base url
    baseUrl = environment.apiUrl+'/farmer/idproof';
    maxSize: number = 10;

    constructor(private http: HttpClient) { }
  
    // Http Headers
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
  
    // POST
    CreateIdProof(data): Observable<ResponseMessage> {
      return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }  
  
    // GET By ID
    GetIdProof(id): Observable<FarmerIdProof> {
      return this.http.get<FarmerIdProof>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // GET
    GetAllIdProof(): Observable<FarmerIdProof> {
      return this.http.get<FarmerIdProof>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // PUT
    UpdateIdProof(id, data): Observable<ResponseMessage> {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // DELETE
    DeleteIdProof(id){
      return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
    
    //GET
getIdProofPagenatedList(page: number, rowSize: number, searchText): Observable<PageFarmerIdProof> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageFarmerIdProof>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}



  // Reject
  RejectIdProof(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeIdProof(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveIdProof(id) {
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
