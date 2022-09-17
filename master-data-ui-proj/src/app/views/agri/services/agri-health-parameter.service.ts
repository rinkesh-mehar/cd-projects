import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriPhenophaseService } from './agri-phenophase.service';
import { AgriHealthParameter } from '../models/AgriHealthParameter';
import { PageAgriHealthParameter } from '../models/PageAgriHealthParameter';

@Injectable({
  providedIn: 'root'
})
export class AgriHealthParameterService {

 // Base url
 baseUrl = environment.apiUrl + '/agri/health-parameter';
 maxSize: number = 10;


 constructor(private http: HttpClient, //agriCommodityService: AgriCommodityService,
   //agriPhenophaseService: AgriPhenophaseService
   ) { }

 // Http Headers
 httpOptions = {
   headers: new HttpHeaders({
     'Content-Type': 'application/json'
   })
 }

 // POST
 CreateHealthParameter(data): Observable<ResponseMessage> {
   return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

 //  GET By ID
 GetHealthParameter(id): Observable<AgriHealthParameter> {
   return this.http.get<AgriHealthParameter>(this.baseUrl + '/' + id)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

//  //Get
//  GetAllHealthParameterByCommodityId(commodityId): Observable<AgriHealthParameter> {
//    return this.http.get<AgriHealthParameter>(this.baseUrl + '/commodity-id/' + commodityId)
//      .pipe(
//        retry(1),
//        catchError(this.errorHandl)
//      )
//  }

//  //Get
//  GetAllHealthParameterByPhenophaseId(phenophaseId): Observable<AgriHealthParameter> {
//    return this.http.get<AgriHealthParameter>(this.baseUrl + '/phenophase-id/' + phenophaseId)
//      .pipe(
//        retry(1),
//        catchError(this.errorHandl)
//      )
//  }

 // GET
 GetAllHealthParameter(): Observable<AgriHealthParameter> {
   return this.http.get<AgriHealthParameter>(this.baseUrl + '/list')
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

//  //For-Pagination
//  getPageAgriHealthParameter(page: number): Observable<PageAgriHealthParameter> {
//    var url = this.baseUrl + "?page=" + page + "&size=" + environment.pageSize;
//    return this.http.get<PageAgriBioPageAgriHealthParameterticStress>(url)
//      .pipe(
//        map(response => {
//          const data = response;
//          //console.log(data.content);
//          return data;
//        }));
//  }

 // PUT
 UpdateHealthParameter(id, data): Observable<ResponseMessage> {
   return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

 // DELETE
 DeleteHealthParameter(id) {
   return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

   // Reject
   RejectHealthParameter(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeHealthParameter(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveHealthParameter(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getHealthParameterPagenatedList(page: number, rowSize: number, searchText): Observable<PageAgriHealthParameter> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriHealthParameter>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
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
