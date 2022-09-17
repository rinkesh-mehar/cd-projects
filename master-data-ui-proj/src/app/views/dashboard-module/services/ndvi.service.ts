import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../geo/models/ResponseMessage';
import { Ndvi } from '../models/Ndvi';


@Injectable({
  providedIn: 'root'
})
export class NdviService {

  // Base url
  baseUrl = environment.dashboardApiUrl + '/ndvi';


  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }


  // // GET By ID
  // GetNdvi(id): Observable<Ndvi> {
  //   return this.http.get<Ndvi>(this.baseUrl + '/' +id)
  //     .pipe(
  //       retry(1),
  //       catchError(this.errorHandl)
  //     )
  // }

  // GET Today Data
  GetNdviTodaysData(): Observable<Ndvi> {
    return this.http.get<Ndvi>(this.baseUrl + '/today-data')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET Monthly Data
  GetNdviMonthlyAndYearlyData(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/monthly-data')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Get Weeekly Data
  GetNDVIWeeklyData(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/weekly-data')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get KML Data
  GetTodayKMLData(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/today-kml-data')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get KML Data
  GetWeeklyOfDayKMLOnboardedData(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/weekly-kml-onboarded')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // //Get Sentinel Downloaded
  // GetLast12HoyrlySentinelDownloadedData(): Observable<any>{
  //   return this.http.get<any>(this.baseUrl + '/hourly-sentinelDownloaded-data')
  //   .pipe(
  //     retry(1),
  //     catchError(this.errorHandl)
  //   )
  // }

  // //Get KML Data
  // GetLast12HoyrlySentinelAnalyseData(): Observable<any>{
  //   return this.http.get<any>(this.baseUrl + '/hourly-sentinelAnalysed-data')
  //   .pipe(
  //     retry(1),
  //     catchError(this.errorHandl)
  //   )
  // }

  //   //Get KML Data
  //   GetLast12HoyrlyProcessedNDVIData(): Observable<any>{
  //     return this.http.get<any>(this.baseUrl + '/hourly-processedNDVI-data')
  //     .pipe(
  //       retry(1),
  //       catchError(this.errorHandl)
  //     )
  //   }

  //Get Hourly Data
  GetetHourlyData(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/hourly-data')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get Time Required Sentinal Download
  GetTimeRequiredSentinalDownload(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/time-req-sentinal-down')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }//GetTimeRequiredSentinalDownload

  //Get Time Required Sentinal Analyse
  GetTimeRequiredSentinalAnalyse(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/time-req-sentinal-analysed')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }//GetTimeRequiredSentinalAnalyse

    //Get Time Required Benchmark NDVI
    GetTimeRequiredBenchmarkNDVI(): Observable<any> {
      return this.http.get<any>(this.baseUrl + '/time-req-benchmark-ndvi')
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }//GetTimeRequiredSentinalAnalyse

  //   GetMonthByYear(year): Observable<Ndvi> {
  //    return this.http.get<Ndvi>(this.baseUrl + '/month' + '/' + year , this.httpOptions)
  //      .pipe(
  //        retry(1),
  //        catchError(this.errorHandl)
  //      )
  //  }

  //   GetWeekByYearAndMonth(year,month): Observable<Ndvi> {
  //     return this.http.get<Ndvi>(this.baseUrl + '/weeks' + '/' + year + '/' + month, this.httpOptions)
  //       .pipe(
  //         retry(1),
  //         catchError(this.errorHandl)
  //       )
  //   }

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
