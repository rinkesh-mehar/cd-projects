import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriPhenophaseService } from './agri-phenophase.service';
import { AgriHealthParameterService } from './agri-health-parameter.service';
import { AgriHealth } from '../models/AgriHealth';
import { PageAgriHealth } from '../models/PageAgriHealth';

@Injectable({
  providedIn: 'root'
})
export class AgriHealthService {
  

 // Base url
 baseUrl = environment.apiUrl + '/agri/health';
 maxSize: number = 10;


 constructor(private http: HttpClient, agriCommodityService: AgriCommodityService,
   agriPhenophaseService: AgriPhenophaseService,agriHealthParameter: AgriHealthParameterService) { }

 // Http Headers
 httpOptions = {
   headers: new HttpHeaders({
     'Content-Type': 'application/json'
   })
 }

 // POST
 CreateHealth(data): Observable<ResponseMessage> {
   return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

 //  GET By ID
 GetHealth(id): Observable<AgriHealth> {
   return this.http.get<AgriHealth>(this.baseUrl + '/' + id)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

    //For-Pagination
    getPageAgriHealth(page: number, rowSize: number,searchText, isValid: number,missing): Observable<PageAgriHealth> {
      this.maxSize = rowSize || this.maxSize;
      var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
      return this.http.get<PageAgriHealth>(url)
        .pipe(
          map(response => {
            const data = response;
            console.log(data.content);
            return data;
          }));
    }

 //Get
 GetAllHealthByCommodityId(commodityId): Observable<AgriHealth> {
   return this.http.get<AgriHealth>(this.baseUrl + '/commodity-id/' + commodityId)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

 //Get
 GetAllHealthByPhenophaseId(phenophaseId): Observable<AgriHealth> {
   return this.http.get<AgriHealth>(this.baseUrl + '/phenophase-id/' + phenophaseId)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

  //Get
  GetAllHealthByWeatherParameterId(weatherParameterId): Observable<AgriHealth> {
    return this.http.get<AgriHealth>(this.baseUrl + '/weatherParameter-id/' + weatherParameterId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

 // GET
 GetAllHealth(): Observable<AgriHealth> {
   return this.http.get<AgriHealth>(this.baseUrl + '/list')
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

//  //For-Pagination
//  getPageAgriHealth(page: number): Observable<PageAgriHealth> {
//    var url = this.baseUrl + "?page=" + page + "&size=" + environment.pageSize;
//    return this.http.get<PageAgriHealth>(url)
//      .pipe(
//        map(response => {
//          const data = response;
//          //console.log(data.content);
//          return data;
//        }));
//  }

 // PUT
 UpdateHealth(id, data): Observable<ResponseMessage> {
   return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

 // DELETE
 DeleteHealth(id) {
   return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
 }

    // Reject
    RejectHealth(id) {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }
  
    //  Finalize
    FinalizeHealth(id) {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }
  
  
    // Approve
    ApproveHealth(id) {
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

