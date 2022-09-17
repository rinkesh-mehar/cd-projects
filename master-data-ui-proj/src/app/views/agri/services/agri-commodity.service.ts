import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { AgriCommodity } from '../models/AgriCommodity';
import { environment } from '../../../../environments/environment';
import { PageAgriCommodity } from '../models/PageAgriCommodity';
import {CommodityAlias} from '../add-agri-commodity-alias/model/CommodityAlias';

@Injectable({
  providedIn: 'root'
})
export class AgriCommodityService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/commodity';
  maxSize: number = 10;

  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  // POST
  CreateCommodity(data: any, uploadedLogo: any): Observable<ResponseMessage> {
    const formData: FormData = new FormData;
        console.log("uploadedLogo : " + uploadedLogo);
       if (uploadedLogo != undefined){

           formData.append('logoFile', uploadedLogo);
       }
        formData.append('data', JSON.stringify(data));
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  // GET By ID
  GetCommodity(id): Observable<AgriCommodity> {
    return this.http.get<AgriCommodity>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  // GET
  GetAllCommoditise(): Observable<AgriCommodity> {
    return this.http.get<AgriCommodity>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  // For-Pagination
  getPageAgriCommodity(page: number, rowSize: number, searchText): Observable<PageAgriCommodity> {
    this.maxSize = rowSize || this.maxSize;
    let url = this.baseUrl + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.http.get<PageAgriCommodity>(url)
      .pipe(
        map(response => {
          const data = response;
          // console.log(data.content);
          return data;
        }));
  }

  // Get Commodity By State Code
  getCommodityByState(stateCode: number): Observable<AgriCommodity> {
    return this.http.get<AgriCommodity>(this.baseUrl + '/' + stateCode + '/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    );
  }

  // Get SeasonId
  getCommodityBySeasonId(seasonId): Observable<AgriCommodity> {
    return this.http.get<AgriCommodity>(this.baseUrl + '/commodity?seasonId=' +   seasonId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    );
  }

  // get PhenophaseId
  getCommodityByPhenophaseId(commodityId): Observable<AgriCommodity> {
    return this.http.get<AgriCommodity>(this.baseUrl + '/commodity/phenophase?commodityId=' +  commodityId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    );
  }

   // get Districts by Districts and SeasonId
   getCommodityByStateCodeAndSeasonId(stateCode, seasonId): Observable<AgriCommodity> {
    return this.http.get<AgriCommodity>(this.baseUrl + '/commodity?stateCode=' +  stateCode + '&seasonId=' +   seasonId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    );
  }

  // //For-Pagination
  // getPageAgriCommodity(page: number): Observable<PageAgriCommodity> {
  //   var url = this.baseUrl + "?page=" + page + "&size=" + environment.pageSize;
  //   return this.http.get<PageAgriCommodity>(url)
  //     .pipe(
  //       map(response => {
  //         const data = response;
  //         //console.log(data.content);
  //         return data;
  //       }));
  // }

  // PUT
  UpdateCommodity(id, data:any, uploadedLogo: any): Observable<ResponseMessage> {

    const formData: FormData = new FormData;
    if (uploadedLogo != undefined){

        formData.append('logoFile', uploadedLogo);
    }
    formData.append('data', JSON.stringify(data));

    return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  // DELETE
  DeleteCommodity(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  // DELETE
  RejectCommodity(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  // DELETE
  FinalizeCommodity(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }


  // DELETE
  ApproveCommodity(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  downloadExcelFormat() {
    return this.http.get(this.baseUrl + '/download-excel-format')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
   }// downloadExcelFormat

  GetAllCommodittAlias(): Observable<CommodityAlias> {
    return this.http.get<CommodityAlias>(this.baseUrl + '/alias')
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        );
  }

  // POST
  saveCommodityAlias(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/alias/tag', JSON.stringify(data), this.httpOptions)
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        );
  }


  getPageAgriCommodityAlias(page: number): Observable<any> {
    let url = this.baseUrl + '/alias/page' + '?page=' + page + '&size=' + environment.pageSize;
    return this.http.get<any>(url)
        .pipe(
            map(response => {
              const data = response;
              // console.log(data.content);
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
