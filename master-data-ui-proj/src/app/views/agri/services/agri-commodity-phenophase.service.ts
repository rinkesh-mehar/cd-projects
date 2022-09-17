import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriPhenophaseService } from './agri-phenophase.service';
import { AgriCommodityPhenophase } from '../models/AgriCommodityPhenophase';
import { PageAgriCommodityPhenophase } from '../models/PageAgriCommodityPhenophase';



@Injectable({
  providedIn: 'root'
})
export class AgriCommodityPhenophaseService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/commodity-phenophase';
  maxSize: number = 10;


  constructor(private http: HttpClient, agriCommodityService: AgriCommodityService,
    agriPhenophaseService: AgriPhenophaseService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateCommodityPhenophase(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  GET By ID
  GetAgriCommodityPhenophase(id): Observable<AgriCommodityPhenophase> {
    return this.http.get<AgriCommodityPhenophase>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllAgriCommodityPhenophaseByCommodityId(commodityId): Observable<AgriCommodityPhenophase> {
    return this.http.get<AgriCommodityPhenophase>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllAgriCommodityPhenophaseByPhenophaseId(phenophaseId): Observable<AgriCommodityPhenophase> {
    return this.http.get<AgriCommodityPhenophase>(this.baseUrl + '/phenophase-id/' + phenophaseId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  // GET
  GetAllAgriCommodityPhenophase(): Observable<AgriCommodityPhenophase> {
    return this.http.get<AgriCommodityPhenophase>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  //Get
  GetByCommodityId(commodityId): Observable<AgriCommodityPhenophase>{
    return this.http.get<AgriCommodityPhenophase>(this.baseUrl+ '/' + commodityId + '/phenophase')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  //For-Pagination
  getPageAgriCommodityPhenophase(page: number, rowSize: number, searchText,missing): Observable<PageAgriCommodityPhenophase> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageAgriCommodityPhenophase>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
        }));
  }

  // PUT
  UpdateCommodityPhenophase(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteCommodityPhenophase(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectCommodityPhenophase(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeCommodityPhenophase(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveCommodityPhenophase(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


    // POST
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