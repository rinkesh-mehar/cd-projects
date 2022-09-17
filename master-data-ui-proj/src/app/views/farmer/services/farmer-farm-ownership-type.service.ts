import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { FarmerFarmOwnershipType } from '../models/FarmerFarmOwnershipType';
import { environment } from '../../../../environments/environment';
import { PageFarmerFarmOwnershipType } from '../models/PageFarmerEducation copy';

@Injectable({
  providedIn: 'root'
})
export class FarmerFarmOwnershipTypeService {

    // Base url
    baseUrl = environment.apiUrl+'/farmer/farm-ownership-type';
    maxSize: number = 10;

    constructor(private http: HttpClient) { }
  
    // Http Headers
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
  
    // POST
    CreateFarmOwnershipType(data): Observable<ResponseMessage> {
      return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }  
  
    // GET By ID
    GetFarmOwnershipType(id): Observable<FarmerFarmOwnershipType> {
      return this.http.get<FarmerFarmOwnershipType>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // GET
    GetAllFarmOwnershipType(): Observable<FarmerFarmOwnershipType> {
      return this.http.get<FarmerFarmOwnershipType>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // PUT
    UpdateFarmOwnershipType(id, data): Observable<ResponseMessage> {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // DELETE
    DeleteFarmOwnershipType(id){
      return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

              // Reject
  RejectFarmOwnershipType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeFarmOwnershipType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveFarmOwnershipType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getFarmOwnershipTypePagenatedList(page: number, rowSize: number, searchText): Observable<PageFarmerFarmOwnershipType> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageFarmerFarmOwnershipType>(url)
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