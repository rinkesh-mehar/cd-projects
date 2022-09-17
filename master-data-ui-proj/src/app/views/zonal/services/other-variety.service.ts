import { PageOtherVariety } from './../models/PageOtherVariety';
import { map } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OtherVarietyService {

  baseUrl = environment.apiUrl + '/zonal/other-variety';
  maxSize: number = 10;
  
  constructor(private http: HttpClient,) { }

   // Http Headers
   httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};


  getPageOtherVariety(page: number, rowSize: number, searchText): Observable<PageOtherVariety> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "/paginatedList" +"?page=" + page + "&size=" + this.maxSize  + "&searchText=" + searchText;
    return this.http.get<PageOtherVariety>(url)
        .pipe(
            map(response => {
                const data = response;
                //console.log(data.content);
                return data;
            }));
}
completed(otherVariety: any): Observable<PageOtherVariety> {
  console.log("Inside Service..")
  return this.http.post<PageOtherVariety>(this.baseUrl + '/completed', JSON.stringify(otherVariety),this.httpOptions)
}
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
