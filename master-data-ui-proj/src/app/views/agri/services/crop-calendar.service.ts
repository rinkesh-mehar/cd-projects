import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { AgriVariety } from '../models/Agrivariety';
import { AgriCommodityService } from './agri-commodity.service';
import { environment } from '../../../../environments/environment';
import { PageAgriVariety } from '../models/PageAgriVariety';

@Injectable({
    providedIn: 'root'
  })
  export class CropCalendarService {
  
    // Base url
    baseUrl = environment.apiUrl + '/crop/calendar';

    constructor(private http: HttpClient, commodityService: AgriCommodityService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // GET
  GetAllCropCalendarList(): Observable<AgriVariety> {
    return this.http.get<AgriVariety>(this.baseUrl + '/list')
      .pipe(
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