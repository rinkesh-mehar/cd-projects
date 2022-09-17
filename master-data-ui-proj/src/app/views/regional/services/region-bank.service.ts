import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { environment } from '../../../../environments/environment';
import { PageRegionBank } from '../models/PageRegionBank';
import { RegionBank } from '../models/RegionBank';

@Injectable({
  providedIn: 'root'
})
export class RegionBankService {

   // Base url
   baseUrl = environment.apiUrl + '/regional/bank';
   maxSize: number = 10;


   constructor(
     private http: HttpClient, 
     geoRegionService: GeoRegionService, 
   ) { }
 
   // Http Headers
   httpOptions = {
     headers: new HttpHeaders({
       'Content-Type': 'application/json'
     })
   }
 
   // POST
   CreateBank(data): Observable<ResponseMessage> {
     return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
 
   // GET By ID
   GetBank(id): Observable<RegionBank> {
     return this.http.get<RegionBank>(this.baseUrl + '/' + id)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
 
   // GET
   GetAllBank(): Observable<RegionBank> {
     return this.http.get<RegionBank>(this.baseUrl + '/list')
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
   //For-Pagination
   getPageRegionBank(page: number, rowSize: number,searchText, isValid: number,missing): Observable<PageRegionBank> {
    this.maxSize = rowSize || this.maxSize;
     var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
     return this.http.get<PageRegionBank>(url)
       .pipe(
         map(response => {
           const data = response;
           console.log(data.content);
           return data;
         }));
   }
 
   // PUT
   UpdateBank(id, data): Observable<ResponseMessage> {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
 
   // DELETE
   DeleteBank(id) {
     return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
 
 
   // Reject
   RejectBank(id) {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
 
   //  Finalize
   FinalizeBank(id) {
     return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
 
 
   // Approve
   ApproveBank(id) {
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
