import { ZonalCommodityCultivationCost } from './../models/ZonalCommodityCultivationCost';
import { ResponseMessage } from './../../general/models/ResponseMessage';
import { map, retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { PageZonalCommodityCultivationCost } from '../models/PageZonalCommodityCultivationCost';

@Injectable({
  providedIn: 'root'
})
export class ZonalCommodityCultivationCostService {
  // Base url
  baseUrl = environment.apiUrl + '/zonal/commodity-cultivation-cost';
  maxSize: number = 10;

  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
      headers: new HttpHeaders({
          'Content-Type': 'application/json'
      })
  }

  getPageZonalCommodityCultivationCost(page: number, rowSize: number, searchText): Observable<PageZonalCommodityCultivationCost> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "/paginatedList" +"?page=" + page + "&size=" + this.maxSize + "&searchText=" + searchText;
    return this.http.get<PageZonalCommodityCultivationCost>(url)
        .pipe(
            map(response => {
                const data = response;
                //console.log(data.content);
                return data;
            }));
}
// POST
CreateCultivationCost(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
// PUT
UpdateCultivationCost(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  // GET By ID
  GetCultivationCost(id): Observable<ZonalCommodityCultivationCost> {
    return this.http.get<ZonalCommodityCultivationCost>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
// Reject
RejectCultivationCost(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      )
}

//  Finalize
FinalizeCultivationCost(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      )
}


// Approve
ApproveCultivationCost(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      )
}
// DELETE
DeleteCultivationCost(id) {
  return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
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
