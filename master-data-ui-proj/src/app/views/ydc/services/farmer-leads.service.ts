import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { FarmerLeads } from '../farmer-leads/Models/FarmerLeads';
import { ResponseMessage } from '../farmer-leads/Models/ResponseMessage';


@Injectable({
  providedIn: 'root'
})
export class FarmerLeadsService {

 
  // Base url
  baseUrl = environment.ydcApiUrl + '/farmer-leads';


  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateFarmerLeads(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetFarmerLeads(id): Observable<FarmerLeads> {
    return this.http.get<FarmerLeads>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllFarmerLeads(): Observable<FarmerLeads> {
    return this.http.get<FarmerLeads>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  GetFarmerYDCById(uUid): Observable<FarmerLeads> {
    return this.http.get<FarmerLeads>(this.baseUrl + '/ydc' + '/' + uUid , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // //For-Pagination
  // getPageGeoVillage(page: number,searchText): Observable<PageGeoVillage> {
  //   var url = this.baseUrl + "?page=" + page + "&size=" + environment.pageSize + "&searchText=" + searchText;
  //   return this.http.get<PageGeoVillage>(url)
  //     .pipe(
  //       map(response => {
  //         const data = response;
  //         //console.log(data.content);
  //         return data;
  //       }));
  // }

  // // PUT
  // UpdateVillage(id, data): Observable<ResponseMessage> {
  //   return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
  //     .pipe(
  //       retry(1),
  //       catchError(this.errorHandl)
  //     )
  // }

  // // DELETE
  // DeleteVillage(id) {
  //   return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
  //     .pipe(
  //       retry(1),
  //       catchError(this.errorHandl)
  //     )
  // }

  // //  Finalize
  // FinalizeVillage(id) {
  //   return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
  //     .pipe(
  //       retry(1),
  //       catchError(this.errorHandl)
  //     )
  // }

  // // Reject
  // RejectVillage(id) {
  //   return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
  //     .pipe(
  //       retry(1),
  //       catchError(this.errorHandl)
  //     )
  // }

  // // Approve
  // ApproveVillage(id) {
  //   return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
  //     .pipe(
  //       retry(1),
  //       catchError(this.errorHandl)
  //     )
  // }

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
