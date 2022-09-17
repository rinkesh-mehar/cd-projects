import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriPhenophaseService } from './agri-phenophase.service';
import { ZonalFavourableWeather } from '../models/ZonalFavourableWeather';
import { PageZonalFavourableWeather } from '../models/PageZonalFavourableWeather';

@Injectable({
  providedIn: 'root'
})
export class ZonalFavourableWeatherService {


  // Base url
  baseUrl = environment.apiUrl + '/zonal/favourable-weather';
  maxSize: number = 10;


  constructor(private http: HttpClient, agriCommodityService: AgriCommodityService,
    agriPhenophaseService: AgriPhenophaseService, //weatherParamsService : WeatherParamsService
    ) { }
 
  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
 
  // POST
  CreateFavourableWeather(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
 
  //  GET By ID
  GetFavourableWeather(id): Observable<ZonalFavourableWeather> {
    return this.http.get<ZonalFavourableWeather>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  getPageZonalFavourableWeather(page: number,rowSize: number,searchText, isValid: number,missing): Observable<PageZonalFavourableWeather> {
    this.maxSize = rowSize || this.maxSize;
    console.log("missing : " + missing);
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText+ "&missing=" + missing;
    return this.http.get<PageZonalFavourableWeather>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
          return data;
        }));
  }
 
  //Get
  GetAllFavourableWeatherByCommodityId(commodityId): Observable<ZonalFavourableWeather> {
    return this.http.get<ZonalFavourableWeather>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
 
  //Get
  GetAllFavourableWeatherByPhenophaseId(phenophaseId): Observable<ZonalFavourableWeather> {
    return this.http.get<ZonalFavourableWeather>(this.baseUrl + '/phenophase-id/' + phenophaseId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

    //Get
    GetAllFavourableWeatherByWeatherParamsId(weatherParamsId): Observable<ZonalFavourableWeather> {
      return this.http.get<ZonalFavourableWeather>(this.baseUrl + '/weatherParams-id/' + weatherParamsId)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }
 
  // GET
  GetAllFavourableWeather(): Observable<ZonalFavourableWeather> {
    return this.http.get<ZonalFavourableWeather>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
 
 //  //For-Pagination
 //  getPageAgriFavourableWeather(page: number): Observable<PageAgriFavourableWeather> {
 //    var url = this.baseUrl + "?page=" + page + "&size=" + environment.pageSize;
 //    return this.http.get<PageAgriFavourableWeather>(url)
 //      .pipe(
 //        map(response => {
 //          const data = response;
 //          //console.log(data.content);
 //          return data;
 //        }));
 //  }
 
  // PUT
  UpdateFavourableWeather(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
 
  // DELETE
  DeleteFavourableWeather(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

    // Reject
    RejectFavourableWeather(id) {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }
  
    //  Finalize
    FinalizeFavourableWeather(id) {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }
  
  
    // Approve
    ApproveFavourableWeather(id) {
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
