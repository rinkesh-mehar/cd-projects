import { PageFarmerVipDesignation } from './../models/PageFarmerVipDesignation';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { FarmerVipDesignation } from '../models/farmerVipDesignation';

@Injectable({
  providedIn: 'root'
})
export class FarmerVipDesignationService {


  // Base url
  baseUrl = environment.apiUrl + '/farmer/vip-designation';
  maxSize: number = 10;


  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateVipDesignation(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetVipDesignation(id): Observable<FarmerVipDesignation> {
    return this.http.get<FarmerVipDesignation>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllVipDesignation(): Observable<FarmerVipDesignation> {
    return this.http.get<FarmerVipDesignation>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  // getPageFarmerVipDesignation(page: number): Observable<PageFarmerVipDesignation> {
  //   var url = this.baseUrl + "?page=" + page + "&size=" + environment.pageSize;

  //   return this.http.get<PageFarmerVipDesignation>(url)
  //     .pipe(
  //       map(response => {
  //         const data = response;
  //         //console.log(data.content);
  //         return data;
  //       }));
  // }

  //GET
getFarmerVipDesignationPagenatedList(page: number, rowSize: number, searchText): Observable<PageFarmerVipDesignation> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageFarmerVipDesignation>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

  // PUT
  UpdateVipDesignation(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteVipDesignation(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

   // Reject
   RejectVipDesignation(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeVipDesignation(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveVipDesignation(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Error handling
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
