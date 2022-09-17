import { PageAgriPhenophase } from './../models/PageAgriPhenophase';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { AgriPhenophase } from '../models/AgriPhenophase';
import { environment } from '../../../../environments/environment';
import { AgriCommodityService } from './agri-commodity.service';


@Injectable({
  providedIn: 'root'
})
export class AgriPhenophaseService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/phenophase';
  maxSize: number = 10;

  constructor(private http: HttpClient, commodityService: AgriCommodityService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreatePhenophase(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetPhenophase(id): Observable<AgriPhenophase> {
    return this.http.get<AgriPhenophase>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

    //For-Pagination
    getPageAgriPhenophase(page: number,searchText): Observable<AgriPhenophase> {
      var url = this.baseUrl + "?page=" + page + "&size=" + environment.pageSize + "&searchText=" + searchText;
      return this.http.get<AgriPhenophase>(url)
        .pipe(
          map(response => {
            const data = response;
            //console.log(data.content);
            return data;
          }));
    }

  // GET
  GetAllPhenophase(): Observable<AgriPhenophase> {
    return this.http.get<AgriPhenophase>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllPhenophaseByCommodityId(commodityId): Observable<AgriPhenophase> {
    return this.http.get<AgriPhenophase>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdatePhenophase(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeletePhenophase(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectPhenophase(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizePhenophase(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApprovePhenophase(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getPhenophasePagenatedList(page: number, rowSize: number, searchText): Observable<PageAgriPhenophase> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriPhenophase>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

// GET
getPhenophaseListByZonalVarietyId(zonalVarietyId): Observable<AgriPhenophase> {
  return this.http.get<AgriPhenophase>(this.baseUrl+'/phenophase-list-by-zonalVarietyId/' + zonalVarietyId)
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

// GET
getPhenophaseListByZonalCommodityId(zonalCommodityId): Observable<AgriPhenophase> {
  return this.http.get<AgriPhenophase>(this.baseUrl+'/phenophase-list-by-zonalCommodityId/' + zonalCommodityId)
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