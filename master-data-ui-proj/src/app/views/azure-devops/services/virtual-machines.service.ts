import { VirtualMachines } from './../models/virtual-machines';
import { ResponseMessage } from './../../farmer/models/ResponseMessage';
import { catchError, map, retry } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { PageVirtualMachines } from '../models/page-virtual-machines';

@Injectable({
  providedIn: 'root'
})
export class VirtualMachinesService {

// baseUrls = environment.apiUrl + '/deployments';
baseUrls = environment.azureDevopsUrl + '/virtual-machines';

maxSize: number = 10;
constructor(private http: HttpClient) { }

httpOptions = {
  headers: new HttpHeaders({
      'Content-Type': 'application/json'
  })
};

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

// POST
addVirtualMachines(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");
  
  return this.http.post<ResponseMessage>(this.baseUrls + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }
  
  //GET
  getVirtualMachineById(id): Observable<VirtualMachines> {
  return this.http.get<VirtualMachines>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
  }
  
  
  //PUT
  updateVirtualMachines(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
  }
  
  //GET
  getVirtualMachineListByPagenation(page: number, rowSize: number, searchText): Observable<PageVirtualMachines> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageVirtualMachines>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
  }

  // DELETE
  deleteVirtualMachines(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/delete/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
 }

 // GET
 getActiveVirtualMachineList(): Observable<any> {
  return this.http.get(this.baseUrls + '/active-vm-list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
 }

  //GET
  getPassoword(id): Observable<any> {
    return this.http.get<any>(this.baseUrls + '/get-password/' + id)
        .pipe(
            catchError(this.errorHandl)
        );
    }

}
