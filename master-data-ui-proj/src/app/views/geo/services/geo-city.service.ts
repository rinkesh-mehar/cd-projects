import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { GeoCity } from '../models/GeoCity';
import { environment } from '../../../../environments/environment';
import { PageGeoCity } from '../models/PageGeoCity';

@Injectable({
  providedIn: 'root'
})
export class GeoCityService {
// Base url
baseUrl = environment.apiUrl + '/geo/city';


constructor(private http: HttpClient) { }

// Http Headers
httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

// POST
CreateCity(data): Observable<ResponseMessage> {
  return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// GET By ID
GetCity(id): Observable<GeoCity> {
  return this.http.get<GeoCity>(this.baseUrl + '/' + id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// GET
GetAllCity(): Observable<GeoCity> {
  return this.http.get<GeoCity>(this.baseUrl + '/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//For-Pagination
getPageGeoCity(page: number,searchText): Observable<PageGeoCity> {
  var url = this.baseUrl + "?page=" + page + "&size=" + environment.pageSize + "&searchText=" + searchText;
  return this.http.get<PageGeoCity>(url)
    .pipe(
      map(response => {
        const data = response;
        //console.log(data.content);
        return data;
      }));
}


  // GET
  GetAllCityByStateCode(stateCode): Observable<GeoCity> {
    return this.http.get<GeoCity>(this.baseUrl + '/state-code/' + stateCode)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

   // GET
   GetAllCityByDistrictCode(districtCode): Observable<GeoCity> {
    return this.http.get<GeoCity>(this.baseUrl + '/district-code/' + districtCode)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

// PUT
UpdateCity(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// DELETE
DeleteCity(id) {
  return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//  Finalize
FinalizeCity(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// Reject
RejectCity(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// Approve
ApproveCity(id) {
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
